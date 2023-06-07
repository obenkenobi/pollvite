package com.pollvite.polledgeservice.advice

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException


@RestControllerAdvice
class RestAdvice {
    companion object {
        val log: Logger = LoggerFactory.getLogger(RestAdvice::class.java)
    }

    @ExceptionHandler(WebExchangeBindException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun serverExceptionHandler(ex: WebExchangeBindException): MutableList<ObjectError> {
        log.debug("Rest Data Validation Violation", ex)
        return ex.allErrors
    }

    @ExceptionHandler(io.grpc.StatusRuntimeException::class)
    fun grpcExceptionHandler(ex: io.grpc.StatusRuntimeException): ResponseEntity<Any> {
        log.debug("Grpc Exception", ex)
        val status = when(ex.status.code) {
            io.grpc.Status.INVALID_ARGUMENT.code -> HttpStatus.BAD_REQUEST
            io.grpc.Status.NOT_FOUND.code -> HttpStatus.NOT_FOUND
            io.grpc.Status.UNAUTHENTICATED.code -> HttpStatus.UNAUTHORIZED
            io.grpc.Status.PERMISSION_DENIED.code -> HttpStatus.FORBIDDEN
            io.grpc.Status.INTERNAL.code -> HttpStatus.INTERNAL_SERVER_ERROR
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }
        val message = when(status) {
            HttpStatus.BAD_REQUEST, HttpStatus.NOT_FOUND -> ex.status.description
            else -> status.reasonPhrase
        }
        return ResponseEntity.status(status).body(message)
    }
}