package com.pollvite.polledgeservice.services

import com.pollvite.polledgeservice.security.Credentials
import com.pollvite.polledgeservice.security.UserPrincipal
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

interface SecurityService {
    val userPrincipal: Mono<UserPrincipal>
    val credentials: Mono<Credentials>
}

@Service
class SecurityServiceImpl: SecurityService {

    override val userPrincipal: Mono<UserPrincipal>
        get() = ReactiveSecurityContextHolder.getContext().flatMap { securityContext ->
            val principal = securityContext.authentication.principal
            val userPrincipal = if (principal is UserPrincipal) principal else null
            Mono.justOrEmpty(userPrincipal)
        }
    override val credentials: Mono<Credentials>
        get() = ReactiveSecurityContextHolder.getContext().map { securityContext ->
            securityContext.authentication.credentials as Credentials
        }
}