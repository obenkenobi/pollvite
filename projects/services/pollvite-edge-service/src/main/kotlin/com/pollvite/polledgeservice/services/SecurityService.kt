package com.pollvite.polledgeservice.services

import com.pollvite.polledgeservice.dtos.LoginDto
import com.pollvite.polledgeservice.security.CookieUtils
import com.pollvite.polledgeservice.security.Credentials
import com.pollvite.polledgeservice.security.UserPrincipal
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

interface SecurityService {
    val userPrincipal:UserPrincipal?
    val credentials: Credentials?
    fun verifyToken(request: HttpServletRequest): Authentication?
    fun createLoginCookie(loginDto: LoginDto): ResponseCookie?
    fun createLogoutCookie(): ResponseCookie
}

@Service
class SecurityServiceImpl(@Autowired private val firebaseService: FirebaseService,
                          @Autowired private val cookieUtils: CookieUtils
): SecurityService {

    override val userPrincipal: UserPrincipal?
        get() {
            val securityContext = SecurityContextHolder.getContext()
            val principal = securityContext?.authentication?.principal
            return if (principal is UserPrincipal) principal else null
        }
    override val credentials: Credentials?
        get() {
            val securityContext = SecurityContextHolder.getContext()
            return securityContext?.authentication?.credentials as Credentials?
        }

    override fun verifyToken(request: HttpServletRequest): Authentication? {
        val session = cookieUtils.getSessionCookieOrNone(request) ?: return null
        return firebaseService.validateSession(session, Credentials.Type.SESSION)
    }

    override fun createLoginCookie(loginDto: LoginDto): ResponseCookie? {
        val jwt = firebaseService.createSessionJwt(loginDto) ?: return null
        return cookieUtils.createSessionCookie(jwt)
    }

    override fun createLogoutCookie(): ResponseCookie {
        return cookieUtils.createLogoutCookie()
    }
}