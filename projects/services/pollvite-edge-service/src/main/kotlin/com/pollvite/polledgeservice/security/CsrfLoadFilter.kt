package com.pollvite.polledgeservice.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.web.csrf.DeferredCsrfToken
import org.springframework.security.web.server.csrf.CsrfToken
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

// Needed to load csrf token
@Component
class CsrfLoadFilter : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val attr = request.getAttribute(DeferredCsrfToken::class.java.name)
        if (attr is DeferredCsrfToken) {
            attr.get()
        }
        filterChain.doFilter(request, response)
    }

}