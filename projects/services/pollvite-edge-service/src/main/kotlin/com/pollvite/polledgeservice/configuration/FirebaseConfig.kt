package com.pollvite.polledgeservice.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import com.pollvite.polledgeservice.configuration.FirebaseConfig.AdminConfig
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "firebase")
class FirebaseConfig {
    var admin: AdminConfig? = null

    class AdminConfig {
        var serviceAccountPath: String? = null
    }
}