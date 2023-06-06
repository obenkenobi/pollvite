package com.pollvite.pollservice.advice

import io.grpc.Status
import net.devh.boot.grpc.server.advice.GrpcAdvice
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@GrpcAdvice
class GrpcExceptionAdvice {
    companion object {
        val log: Logger = LoggerFactory.getLogger(GrpcExceptionAdvice::class.java)
    }

    @GrpcExceptionHandler
    fun handleException(e: Exception): Status {
        log.debug("Handling Exception from GRPC Advice: {}", e)
        return Status.INTERNAL;
    }
}