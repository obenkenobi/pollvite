package com.pollvite.polledgeservice.security

import org.springframework.security.web.server.csrf.CsrfToken
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

// Needed to load csrf token
@Component
class CsrfLoadFilter : WebFilter {
    override fun filter(exchange: ServerWebExchange, next: WebFilterChain): Mono<Void> = exchange
        .getAttribute<Mono<CsrfToken>>(CsrfToken::class.java.name)
        ?.doOnSuccess { } // do nothing, just subscribe :/
        ?.then(next.filter(exchange)) ?: next.filter(exchange)

}