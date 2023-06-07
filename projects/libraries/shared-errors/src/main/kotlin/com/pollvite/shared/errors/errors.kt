package com.pollvite.shared.errors

enum class ErrorStatus(val description: String) {
    CLIENT("Client Error"),
    BR_VIOLATION("Business Rule Violation"),
    NOT_FOUND("Not Found"),
    UNAUTHENTICATED("Unauthenticated"),
    UNAUTHORIZED("Unauthorized"),
    INTERNAL("Internal Error");

    fun toGrpcStatus(): io.grpc.Status {
        return when(this) {
            CLIENT, BR_VIOLATION -> io.grpc.Status.INVALID_ARGUMENT
            NOT_FOUND -> io.grpc.Status.NOT_FOUND
            UNAUTHENTICATED -> io.grpc.Status.UNAUTHENTICATED
            UNAUTHORIZED -> io.grpc.Status.PERMISSION_DENIED
            INTERNAL -> io.grpc.Status.INTERNAL
        }.withDescription(this.description)
    }
}

class AppException(val status: ErrorStatus, message: String = status.description, cause: Throwable? = null)
    : Exception(message, cause) {

    private fun getPublicDescription(): String {
        return when(status) {
            ErrorStatus.INTERNAL -> status.description
            else -> message ?: status.description
        }
    }

    fun asGrpcStatus(): io.grpc.Status {
        return status.toGrpcStatus().withDescription(getPublicDescription())
    }
}