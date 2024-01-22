package com.pollvite.identityservice.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "jobs")
class JobProps {
    var userProfileBatchCount: Long = 20
    var userProfileBatchNumber: Long = kotlin.random.Random.nextLong(0, 19)
}