package com.pollvite.polledgeservice.security

import com.pollvite.polledgeservice.services.FirebaseService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class AuthFilter(@Autowired private val firebaseService: FirebaseService,
                 @Autowired private val cookieUtils: CookieUtils): OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest,
                                  response: HttpServletResponse, filterChain: FilterChain) {
        verifyToken(request)
        filterChain.doFilter(request, response);
    }

    private fun verifyToken(request: HttpServletRequest) {
        val session = cookieUtils.getSessionCookieOrNone(request) ?: return
        val authentication = firebaseService.validateSession(session, Credentials.Type.SESSION)
        SecurityContextHolder.getContext().authentication = authentication
    }
}