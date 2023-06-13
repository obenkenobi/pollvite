package com.pollvite.polledgeservice.services

import com.pollvite.polledgeservice.security.Credentials
import com.pollvite.polledgeservice.security.UserPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

interface SecurityService {
    val userPrincipal:UserPrincipal?
    val credentials: Credentials?
}

@Service
class SecurityServiceImpl: SecurityService {

    override val userPrincipal: UserPrincipal?
        get() {
            val securityContext = SecurityContextHolder.getContext()
            val principal = securityContext?.authentication?.principal
            return if (principal is UserPrincipal) principal else null
        }
    override val credentials: Credentials?
        get() {
            val securityContext = SecurityContextHolder.getContext()
            return securityContext?.authentication?.credentials as Credentials?
        }
}