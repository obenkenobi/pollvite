package com.pollvite.polledgeservice.security

import org.springframework.http.ResponseCookie
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Component

@Component
class CookieUtils {
    companion object {
        const val sessionCookieName = "X-Auth"
    }

    fun createSessionCookie(value: String) = ResponseCookie.fromClientResponse(sessionCookieName, value)
        .maxAge(3600)
        .httpOnly(true)
        .path("/")
        .secure(false) // Todo: should be true in production
        .build()

    fun getSessionCookieOrNone(request: ServerHttpRequest) = request.cookies[sessionCookieName]?.firstOrNull()?.value


}
