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
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.DependsOn
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.io.File


interface FirebaseService {
    fun getWebConfig(): Map<String, Any>
    // If unauthenticated returns empty
    fun createSessionJwt(loginDto: LoginDto): String?
    // If invalid returns empty
    fun validateSession(session: String, type: Credentials.Type): Authentication?
}

@Service
@DependsOn("firebaseApp")
class FirebaseServiceImpl(@Autowired private val firebasePropsConfig: FirebaseProps): FirebaseService {
    private val fbWebConfig: Map<String, Any> = readFirebaseWebConf()

    override fun getWebConfig(): Map<String, Any> {
        return readFirebaseWebConf();
    }

    override fun createSessionJwt(loginDto: LoginDto): String? {
        val auth = FirebaseAuth.getInstance()
        val sessOpt = SessionCookieOptions.builder().setExpiresIn(3600000).build()
        return try {
            auth.createSessionCookie(loginDto.token, sessOpt)
        } catch (e: FirebaseAuthException) {
            null
        }
    }

    override fun validateSession(session: String, type: Credentials.Type): Authentication? {
        val decodedToken: FirebaseToken = try {
            FirebaseAuth.getInstance().verifySessionCookie(session, true)
        } catch (e: FirebaseAuthException) {
            return null
        }
        val user = firebaseTokenToUserPrinciple(decodedToken)
        val credentials = Credentials(type, decodedToken, session)
        return UsernamePasswordAuthenticationToken(user, credentials, null)
    }

    private fun readFirebaseWebConf(): Map<String, Any> {
        val mapper = ObjectMapper();
        val type = mapper.typeFactory.constructMapType(HashMap::class.java, String::class.java, Any::class.java)
        return mapper.readValue(File(firebasePropsConfig.webConfigPath!!), type)
    }

    private fun firebaseTokenToUserPrinciple(decodedToken: FirebaseToken): UserPrincipal = UserPrincipal(
        uuid = decodedToken.uid,
        issuer = decodedToken.issuer,
        isEmailVerified = decodedToken.isEmailVerified
    )
}