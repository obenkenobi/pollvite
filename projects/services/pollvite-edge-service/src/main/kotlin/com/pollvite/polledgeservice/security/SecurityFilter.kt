package com.pollvite.polledgeservice.security

import com.pollvite.polledgeservice.services.FirebaseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import java.util.*

@Component
class SecurityFilter(@Autowired private val firebaseService: FirebaseService,
                     @Autowired private val cookieUtils: CookieUtils): WebFilter {

    override fun filter(
        serverWebExchange: ServerWebExchange,
        webFilterChain: WebFilterChain
    ): Mono<Void> {
        return verifyToken(serverWebExchange.request).flatMap { authentication ->
            val mono = webFilterChain.filter(serverWebExchange)

            if (authentication.isPresent)
                mono.contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication.get()))
            else
                mono

        }
    }

    private fun verifyToken(request: ServerHttpRequest): Mono<Optional<Authentication>> {
        return Mono.justOrEmpty(cookieUtils.getSessionCookieOrNone(request))
            .flatMap { session -> firebaseService.validateSession(session, Credentials.Type.SESSION) }
            .map { Optional.of(it) }
            .defaultIfEmpty(Optional.empty())

    }
}