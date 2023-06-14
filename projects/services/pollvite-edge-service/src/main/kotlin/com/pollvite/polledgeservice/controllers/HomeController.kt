package com.pollvite.polledgeservice.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.view.RedirectView


@Controller
class HomeController {

    @RequestMapping("/")
    fun index(model: Model): RedirectView {
        return RedirectView("/home");
    }

    @RequestMapping("/home")
    fun home(model: Model): String {
        return "home"
    }

}