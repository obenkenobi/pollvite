package com.pollvite.polledgeservice.services

import com.pollvite.polledgeservice.security.Credentials
import com.pollvite.polledgeservice.security.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono


@Service
class SecurityService(@Autowired val securityProps: SecurityProperties? = null) {

    val user: Mono<User>
        get() = ReactiveSecurityContextHolder.getContext().flatMap { securityContext ->
            val principal = securityContext.authentication.principal
            val userPrincipal = if (principal is User) principal else null
            Mono.justOrEmpty(userPrincipal)
        }
    val credentials: Mono<Credentials>
        get() = ReactiveSecurityContextHolder.getContext().map { securityContext ->
            securityContext.authentication.credentials as Credentials
        }
}