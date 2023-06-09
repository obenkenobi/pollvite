package com.pollvite.polledgeservice.controllers

import com.pollvite.polledgeservice.services.FirebaseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("fb")
class FirebaseController(@Autowired private val firebaseService: FirebaseService) {

    @GetMapping("/webConf")
    fun getFBConf(): Map<String, Any> {
        return firebaseService.getWebConfig()
    }

}