package com.pollvite.polledgeservice.services

import com.pollvite.polledgeservice.dtos.LoginDto
import com.pollvite.polledgeservice.security.CookieUtils
import com.pollvite.polledgeservice.security.Credentials
import com.pollvite.polledgeservice.security.UserPrincipal
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseCookie
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.util.*

interface SecurityService {
    val userPrincipal:UserPrincipal?
    val credentials: Credentials?
    fun verifyToken(request: HttpServletRequest): Authentication?
    fun createLoginCookie(loginDto: LoginDto): ResponseCookie?
    fun createLogoutCookie(): ResponseCookie
}

@Service
class SecurityServiceImpl(@Autowired private val cookieUtils: CookieUtils
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
        /* Todo:
        *  1. get session cookie
        *  2. check if session is in cache
        *  3. if a session is in cache, skip to 6.
        *  4. call identity service to get a session
        *  5. cache the session in redis
        *  6. If session is empty, return null
        *  7. Create and return UsernamePasswordAuthenticationToken instance from session
        * */
        val session = cookieUtils.getSessionCookieOrNone(request) ?: return null
        val credentials = Credentials(Credentials.Type.SESSION, HashMap(), session)
        val user = UserPrincipal(
            uuid = UUID.randomUUID().toString(),
            email = "something@gmail.com",
            issuer = "issuer",
            isEmailVerified = true
        )
        return UsernamePasswordAuthenticationToken(user, credentials, null)
    }

    override fun createLoginCookie(loginDto: LoginDto): ResponseCookie? {
        /*
        * use loginDto to call IdentityService to return a session,
        * cache the session in redis,
        * then return the cookie
        * */
        val sessionVal = "sessionVal"
        return cookieUtils.createSessionCookie(sessionVal)
    }

    override fun createLogoutCookie(): ResponseCookie {
        return cookieUtils.createLogoutCookie()
    }
}