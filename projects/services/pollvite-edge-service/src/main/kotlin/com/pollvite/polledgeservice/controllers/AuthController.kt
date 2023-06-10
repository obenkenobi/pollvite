package com.pollvite.polledgeservice.controllers

import com.pollvite.polledgeservice.dtos.LoginDto
import com.pollvite.polledgeservice.security.CookieUtils
import com.pollvite.polledgeservice.services.FirebaseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("api/auth")
class AuthController(@Autowired private val firebaseService: FirebaseService, @Autowired val cookieUtils: CookieUtils) {

    @PostMapping("/login")
    fun login(@RequestBody loginDto: Mono<LoginDto>): Mono<ResponseEntity<Void>> =
        firebaseService.createSessionJwt(loginDto).map<ResponseEntity<Void>?> { jwt ->
            val authCookie = cookieUtils.createSessionCookie(jwt)
            ResponseEntity.noContent().header("Set-Cookie", authCookie.toString()).build()
        }.defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).build())


}