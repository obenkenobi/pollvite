package com.pollvite.userservice.configuration

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.FileInputStream

@Configuration
@ConfigurationProperties(prefix = "firebase")
class FirebaseProps {
    var projectId: String? = null
    var serviceAccountPath: String? = null
}

@Configuration
class FirebaseConfig {

    @Bean(name=["firebaseApp"])
    fun firebaseApp(@Autowired fbPropsConfig: FirebaseProps): FirebaseApp {
        val serviceAccount = FileInputStream(fbPropsConfig.serviceAccountPath!!)

        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setProjectId(fbPropsConfig.projectId)
            .build()

        return FirebaseApp.initializeApp(options)
    }
}