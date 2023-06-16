package com.pollvite.polledgeservice.security

import com.pollvite.polledgeservice.services.SecurityService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class AuthFilter(
    @Autowired private val securityService: SecurityService
): OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest,
                                  response: HttpServletResponse, filterChain: FilterChain) {
        verifyToken(request)
        filterChain.doFilter(request, response);
    }

    private fun verifyToken(request: HttpServletRequest) {
        val authentication = securityService.verifyToken(request) ?: return
        SecurityContextHolder.getContext().authentication = authentication
    }
}