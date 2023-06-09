package com.pollvite.polledgeservice.configuration
//
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.FirebaseAuthException
//import com.google.firebase.auth.FirebaseToken
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.autoconfigure.security.SecurityProperties
//import org.springframework.http.server.reactive.ServerHttpRequest
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
//import org.springframework.security.core.context.SecurityContextHolder
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
//import org.springframework.stereotype.Component
//import org.springframework.web.server.ServerWebExchange
//import org.springframework.web.server.WebFilter
//import org.springframework.web.server.WebFilterChain
//import reactor.core.publisher.Mono
//import java.security.Principal
//
//
//class User(
//    val uuid: String,
//    val issuer: String,
//    val isEmailVerified: Boolean
//) : Principal {
//    override fun getName(): String {
//        return uuid
//    }
//}
//
//class Credentials(val type: Type, decodedToken: FirebaseToken, session: String) {
//    enum class Type {ID_TOKEN, SESSION}
//}
//
//@Component
//class SecurityFilter (
//    @Autowired securityService: SecurityService,
//    @Autowired restSecProps: SecurityProperties,
//    @Autowired cookieUtils: cookieUtils,
//    @Autowired securityProps: SecurityProperties
//): WebFilter {
//
//    override fun filter(
//        serverWebExchange: ServerWebExchange,
//        webFilterChain: WebFilterChain
//    ): Mono<Void> {
//        verifyToken(serverWebExchange.request)
//        return webFilterChain.filter(serverWebExchange)
//    }
//
//    private fun verifyToken(request: ServerHttpRequest) {
//        val session = request.cookies["X-Auth"]?.firstOrNull()?.value ?: return
//        val type = Credentials.Type.SESSION
//        val decodedToken: FirebaseToken = try {
//            FirebaseAuth.getInstance().verifySessionCookie(session, true)
//        } catch (_: FirebaseAuthException) {
//            null
//        } ?: return
//
//        val user = firebaseTokenToUserPrinciple(decodedToken)
//        val authentication = UsernamePasswordAuthenticationToken(
//            user,
//            Credentials(type, decodedToken, session), null
//        )
//        SecurityContextHolder.getContext().authentication = authentication
//    }
//
//    private fun firebaseTokenToUserPrinciple(decodedToken: FirebaseToken): User = User(
//        uuid = decodedToken.uid,
//        issuer = decodedToken.issuer,
//        isEmailVerified = decodedToken.isEmailVerified)
//}