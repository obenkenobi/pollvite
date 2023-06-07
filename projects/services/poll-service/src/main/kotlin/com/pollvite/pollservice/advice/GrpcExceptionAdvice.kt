package com.pollvite.pollservice.advice

import com.pollvite.shared.errors.GrpcErrorHandler
import io.grpc.Status
import net.devh.boot.grpc.server.advice.GrpcAdvice
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler

@GrpcAdvice
class GrpcExceptionAdvice {

    @GrpcExceptionHandler
    fun handleException(e: Exception): Status {
        return GrpcErrorHandler.handle(e)
    }

}