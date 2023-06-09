package com.pollvite.polledgeservice.controllers

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.SessionCookieOptions
import com.pollvite.polledgeservice.dtos.LoginDto
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("auth")
class AuthController {

    @PostMapping("/login")
    fun login(@RequestBody loginDto: Mono<LoginDto>): Mono<ResponseEntity<Void>> = loginDto.map {
        val auth = FirebaseAuth.getInstance()
        val sessOpt = SessionCookieOptions.builder().setExpiresIn(3600000).build()
        val jwt = auth.createSessionCookie(it.token, sessOpt)
        val authCookie = ResponseCookie.fromClientResponse("X-Auth", jwt)
            .maxAge(3600)
            .httpOnly(true)
            .path("/")
            .secure(false) // should be true in production
            .build()
        ResponseEntity.noContent().header("Set-Cookie", authCookie.toString()).build()
    }


}