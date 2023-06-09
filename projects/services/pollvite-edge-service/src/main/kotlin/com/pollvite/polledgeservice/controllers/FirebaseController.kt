package com.pollvite.polledgeservice.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.pollvite.polledgeservice.configuration.FirebasePropsConfig
import com.pollvite.polledgeservice.services.FirebaseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.io.File

@RestController
@RequestMapping("fb")
class FirebaseController(@Autowired private val firebaseService: FirebaseService) {

    @GetMapping("/webConf")
    fun getFBConf(): Map<String, Any> {
        return firebaseService.getWebConfig()
    }

}