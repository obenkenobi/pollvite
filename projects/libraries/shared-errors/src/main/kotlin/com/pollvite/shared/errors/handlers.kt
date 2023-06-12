package com.pollvite.shared.errors

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * A static Grpc error handler
 * */
object GrpcErrorHandler {
    @JvmStatic
    private val log: Logger = LoggerFactory.getLogger(GrpcErrorHandler::class.java)

    /**
     * Handles an [Exception].
     * @param e [Exception] to be handled.
     * @return The appropriate [io.grpc.Status] for the provided [Exception].
     * */
    fun handle(e: Exception): io.grpc.Status {
        log.debug("Handling Exception from GRPC Advice", e)
        return when(e) {
            is AppException -> {
                if (e.status == ErrorStatus.INTERNAL) {
                    logInternalError(e)
                }
                e.asGrpcStatus()
            }
            else -> {
                logInternalError(e)
                ErrorStatus.INTERNAL.toGrpcStatus()
            }
        }
    }

    private fun logInternalError(e: Exception) {
        log.error("Internal error", e)
    }
}
