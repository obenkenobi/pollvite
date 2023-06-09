package com.pollvite.polledgeservice.controllers

import com.pollvite.polledgeservice.configuration.FirebasePropsConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import reactor.core.publisher.Mono


@Controller
class ViewController(@Autowired val firebasePropsConfig: FirebasePropsConfig) {

    @Profile("dev")
    @RequestMapping("/firebase")
    fun index(model: Model): Mono<String> {
        return Mono.fromSupplier {
            return@fromSupplier "firebase"
        }
    }

}