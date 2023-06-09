package com.pollvite.polledgeservice.controllers

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping


@Controller
class ViewController {

    @Profile("dev")
    @RequestMapping("/firebase")
    fun index(model: Model): String {
        return "firebase"
    }

}