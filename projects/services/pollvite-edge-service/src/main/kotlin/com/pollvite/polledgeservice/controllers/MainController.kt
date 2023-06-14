package com.pollvite.polledgeservice.controllers

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping


@Controller
class MainController {

    @RequestMapping("/")
    fun index(model: Model): String {
        return "home"
    }

    @RequestMapping("/home")
    fun home(model: Model): String {
        return "home"
    }

}