package com.pollvite.polledgeservice.security

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Component
import org.springframework.web.util.WebUtils

@Component
class CookieUtils {
    companion object {
        const val SESSION_COOKIE_NAME = "X-Auth"
    }

    fun createSessionCookie(value: String) = ResponseCookie.fromClientResponse(SESSION_COOKIE_NAME, value)
        .maxAge(3600)
        .httpOnly(true)
        .path("/")
        .secure(false) // Todo: should be true in production
        .build()

    fun getSessionCookieOrNone(request: HttpServletRequest) = WebUtils.getCookie(request, SESSION_COOKIE_NAME)?.value


}
