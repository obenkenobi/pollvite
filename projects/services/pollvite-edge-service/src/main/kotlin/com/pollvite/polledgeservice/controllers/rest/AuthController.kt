package com.pollvite.polledgeservice.controllers.rest

import com.pollvite.polledgeservice.dtos.LoginDto
import com.pollvite.polledgeservice.security.CookieUtils
import com.pollvite.polledgeservice.services.FirebaseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/auth")
class AuthController(@Autowired private val firebaseService: FirebaseService,
                     @Autowired private val cookieUtils: CookieUtils) {

    @PostMapping("/login")
    fun login(@RequestBody loginDto: LoginDto) : ResponseEntity<Void> {
        val jwt = firebaseService.createSessionJwt(loginDto)
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).build()
        val authCookie = cookieUtils.createSessionCookie(jwt)
        return ResponseEntity.noContent().header("Set-Cookie", authCookie.toString()).build()
    }

    @GetMapping("/csrf")
    fun csrf() : ResponseEntity<Void> {
        return ResponseEntity.noContent().build()
    }

}