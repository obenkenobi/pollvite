package com.pollvite.pollrestservice.advice

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException


@RestControllerAdvice
class RestExceptionHandlers {
    companion object {
        val log: Logger = LoggerFactory.getLogger(RestExceptionHandlers::class.java)
    }

    @ExceptionHandler(WebExchangeBindException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun serverExceptionHandler(ex: WebExchangeBindException): MutableList<ObjectError> {
        log.debug("Rest Data Validation Violation", ex)
        return ex.allErrors
    }
}