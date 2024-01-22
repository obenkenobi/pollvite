package com.pollvite.identityservice.background

import com.pollvite.identityservice.services.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component


@Component
class UserUpdateJob(@Autowired val userService: UserService) {
    companion object {
        private val log: Logger = LoggerFactory.getLogger(UserUpdateJob::class.java)
    }

    @Scheduled(fixedRate = 5000)
    fun listenChangeStream() {
        userService.doUserSyncActions()
    }
}