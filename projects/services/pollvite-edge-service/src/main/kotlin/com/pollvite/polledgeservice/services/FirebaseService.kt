package com.pollvite.polledgeservice.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.SessionCookieOptions
import com.pollvite.polledgeservice.configuration.FirebasePropsConfig
import com.pollvite.polledgeservice.dtos.LoginDto
import com.pollvite.polledgeservice.utils.toMono
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.io.File


interface FirebaseService {
    fun getWebConfig(): Map<String, Any>
    // If unauthenticated returns empty
    fun createSessionJwt(loginDto: Mono<LoginDto>): Mono<String>
}

@Service
class FirebaseServiceImpl(@Autowired private val firebasePropsConfig: FirebasePropsConfig,
                      @Autowired private val firebaseApp: FirebaseApp): FirebaseService {
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
}