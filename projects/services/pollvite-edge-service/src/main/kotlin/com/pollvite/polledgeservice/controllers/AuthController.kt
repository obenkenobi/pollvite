package com.pollvite.polledgeservice.controllers

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.SessionCookieOptions
import com.pollvite.polledgeservice.dtos.LoginDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("auth")
class AuthController {

    @PostMapping("/login")
    fun login(@RequestBody loginDto: Mono<LoginDto>): Mono<String> = loginDto.map {
        val auth = FirebaseAuth.getInstance()
        val sessOpt = SessionCookieOptions.builder().setExpiresIn(3600000).build()
        val cookie = auth.createSessionCookie(it.token, sessOpt)
        cookie
    }


}