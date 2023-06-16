package com.pollvite.polledgeservice.controllers.mvc

import jakarta.servlet.http.HttpServletRequest
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView


@Controller
@RequestMapping("/error")
class CustomErrorController: ErrorController {

    @RequestMapping
    fun handleError(httpRequest: HttpServletRequest): ModelAndView {
        val errorPage = ModelAndView("error")
        val status = getErrorCode(httpRequest)
        val message = HttpStatus.valueOf(status).reasonPhrase
        errorPage.addObject("status", status)
        errorPage.addObject("message", message)
        return errorPage
    }

    private fun getErrorCode(httpRequest: HttpServletRequest): Int {
        return httpRequest
            .getAttribute("jakarta.servlet.error.status_code") as Int
    }
}