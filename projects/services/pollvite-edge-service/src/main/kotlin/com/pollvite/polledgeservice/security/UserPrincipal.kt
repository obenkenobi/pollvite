package com.pollvite.polledgeservice.security

import java.security.Principal

class UserPrincipal(
    val uuid: String,
    val issuer: String,
    val isEmailVerified: Boolean
) : Principal {
    override fun getName(): String {
        return uuid
    }
}