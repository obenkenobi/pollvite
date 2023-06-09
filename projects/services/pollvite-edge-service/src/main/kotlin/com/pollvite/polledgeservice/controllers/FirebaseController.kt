package com.pollvite.polledgeservice.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.pollvite.polledgeservice.configuration.FirebasePropsConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.io.File

@RestController
@RequestMapping("fb")
class FirebaseController(@Autowired val firebasePropsConfig: FirebasePropsConfig) {

    val fbWebConfig: Map<String, Any> = readFirebaseWebConf()

    private fun readFirebaseWebConf(): Map<String, Any> {
        val mapper = ObjectMapper();
        val type = mapper.typeFactory.constructMapType(HashMap::class.java, String::class.java, Any::class.java)
        return mapper.readValue(File(firebasePropsConfig.webConfigPath!!), type)
    }

    @GetMapping("/fbConf")
    fun getFBConf(): Map<String, Any> {
        return fbWebConfig
    }

}