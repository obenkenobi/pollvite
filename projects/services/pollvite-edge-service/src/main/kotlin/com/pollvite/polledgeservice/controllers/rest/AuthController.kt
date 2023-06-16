package com.pollvite.polledgeservice.controllers.rest

import com.pollvite.polledgeservice.dtos.LoginDto
import com.pollvite.polledgeservice.services.SecurityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/auth")
class AuthController(@Autowired private val securityService: SecurityService) {

    @PostMapping("/login")
    fun login(@RequestBody loginDto: LoginDto) : ResponseEntity<Any> {
        val isLoggedIn = securityService.userPrincipal != null
        if (isLoggedIn) {
            return ResponseEntity.badRequest().body("Already logged in!")
        }
        val loginCookie = securityService.createLoginCookie(loginDto)
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).build()
        return ResponseEntity.noContent().header("Set-Cookie", loginCookie.toString()).build()
    }

    @PostMapping("/logout")
    fun logout() : ResponseEntity<Void> {
        val logoutCookie = securityService.createLogoutCookie()
        return ResponseEntity.noContent().header("Set-Cookie", logoutCookie.toString()).build()
    }

    @GetMapping("/csrf")
    fun csrf() : ResponseEntity<Void> {
        return ResponseEntity.noContent().build()
    }

}