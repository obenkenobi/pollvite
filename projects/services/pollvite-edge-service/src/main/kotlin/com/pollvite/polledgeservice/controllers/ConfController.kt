package com.pollvite.polledgeservice.controllers

import com.pollvite.polledgeservice.services.FirebaseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/conf")
class ConfController(@Autowired private val firebaseService: FirebaseService) {

    @GetMapping("/fb/web")
    fun getFBConf(): Map<String, Any> {
        return firebaseService.getWebConfig()
    }

}