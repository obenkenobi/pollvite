package com.pollvite.polledgeservice.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseToken
import com.google.firebase.auth.SessionCookieOptions
import com.pollvite.polledgeservice.configuration.FirebaseProps
import com.pollvite.polledgeservice.dtos.LoginDto
import com.pollvite.polledgeservice.security.Credentials
import com.pollvite.polledgeservice.security.UserPrincipal
import com.pollvite.polledgeservice.utils.toMono
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.DependsOn
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.io.File
import kotlin.collections.HashMap


interface FirebaseService {
    fun getWebConfig(): Map<String, Any>
    // If unauthenticated returns empty
    fun createSessionJwt(loginDto: Mono<LoginDto>): Mono<String>
    // If invalid returns empty
    fun validateSession(session: String, type: Credentials.Type): Mono<Authentication>
}

@Service
@DependsOn("firebaseApp")
class FirebaseServiceImpl(@Autowired private val firebasePropsConfig: FirebaseProps): FirebaseService {
    private val fbWebConfig: Map<String, Any> = readFirebaseWebConf()

    private fun readFirebaseWebConf(): Map<String, Any> {
        val mapper = ObjectMapper();
        val type = mapper.typeFactory.constructMapType(HashMap::class.java, String::class.java, Any::class.java)
        return mapper.readValue(File(firebasePropsConfig.webConfigPath!!), type)
    }

    override fun getWebConfig(): Map<String, Any> {
        return fbWebConfig;
    }

    override fun createSessionJwt(loginDto: Mono<LoginDto>): Mono<String> = loginDto.flatMap {
        val auth = FirebaseAuth.getInstance()
        val sessOpt = SessionCookieOptions.builder().setExpiresIn(3600000).build()
        val jwtFuture = auth.createSessionCookieAsync(it.token, sessOpt)
        jwtFuture.toMono()
    }.onErrorResume({ it is FirebaseAuthException}, { Mono.empty() })

    override fun validateSession(session: String, type: Credentials.Type): Mono<Authentication> =
        FirebaseAuth.getInstance().verifySessionCookieAsync(session, true).toMono()
            .onErrorResume { e -> if (e is FirebaseAuthException) Mono.empty() else Mono.error(e) }
            .map { decodedToken ->
                val user = firebaseTokenToUserPrinciple(decodedToken)
                val credentials = Credentials(type, decodedToken, session)
                val authentication: Authentication =
                    UsernamePasswordAuthenticationToken(user, credentials, null)
                authentication
            }

    private fun firebaseTokenToUserPrinciple(decodedToken: FirebaseToken): UserPrincipal = UserPrincipal(
        uuid = decodedToken.uid,
        issuer = decodedToken.issuer,
        isEmailVerified = decodedToken.isEmailVerified
    )
}