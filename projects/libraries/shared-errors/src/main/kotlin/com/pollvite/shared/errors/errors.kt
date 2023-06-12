package com.pollvite.shared.errors

/**
 * A general error status that can convert to different protocol status codes like GRPC or HTTP.
 * @param description The description of the [ErrorStatus]
 * */
enum class ErrorStatus(val description: String) {
    CLIENT("Client Error"),
    BUSINESS_RULES_VIOLATION("Business Rules Violation"),
    NOT_FOUND("Not Found"),
    UNAUTHENTICATED("Unauthenticated"),
    UNAUTHORIZED("Unauthorized"),
    INTERNAL("Internal Error");

    /**
     * @return A [io.grpc.Status] that is mapped from an [ErrorStatus] instance.
     * */
    fun toGrpcStatus(): io.grpc.Status {
        return when(this) {
            CLIENT, BUSINESS_RULES_VIOLATION -> io.grpc.Status.INVALID_ARGUMENT
            NOT_FOUND -> io.grpc.Status.NOT_FOUND
            UNAUTHENTICATED -> io.grpc.Status.UNAUTHENTICATED
            UNAUTHORIZED -> io.grpc.Status.PERMISSION_DENIED
            INTERNAL -> io.grpc.Status.INTERNAL
        }.withDescription(this.description)
    }
}

/**
 * An [Exception] Created directly from an application used to represent failure in business or server tasks.
 * @param status The status of the exception.
 * @param message The [Exception] message.
 * @param cause The cause of the [AppException] if caused by a [Throwable].
 * */
class AppException(val status: ErrorStatus, message: String = status.description, cause: Throwable? = null)
    : Exception(message, cause) {

    private fun getPublicDescription(): String {
        return when(status) {
            ErrorStatus.INTERNAL -> status.description
            else -> message ?: status.description
        }
    }

    /**
     * @return An [io.grpc.Status] with a description that is either
     * the [message] for the [AppException]
     * or the [ErrorStatus.description] if the [status] is [ErrorStatus.INTERNAL] or [message] is null.
     * */
    fun asGrpcStatus(): io.grpc.Status {
        return status.toGrpcStatus().withDescription(getPublicDescription())
    }
}