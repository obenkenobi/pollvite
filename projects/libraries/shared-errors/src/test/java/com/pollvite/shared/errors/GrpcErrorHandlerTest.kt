package com.pollvite.shared.errors

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GrpcErrorHandlerTest {
    @Test
    fun testHandleBaseException() {
        val ex = Exception("")
        val status = GrpcErrorHandler.handle(ex)
        assertEquals(io.grpc.Status.INTERNAL.code, status.code)
    }

    @Test
    fun testHandleAppException() {
        val ex = AppException(ErrorStatus.BUSINESS_RULES_VIOLATION)
        val status = GrpcErrorHandler.handle(ex)
        assertEquals(io.grpc.Status.INVALID_ARGUMENT.code, status.code)
    }
}