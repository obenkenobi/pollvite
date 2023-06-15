package com.pollvite.polledgeservice.controllers.mvc

import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/error")
class CustomErrorController: ErrorController {

    @RequestMapping
    fun handleError(): String {
        return "error"
    }
}