package com.pollvite.shared.errors

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GrpcErrorHandlerTest {
    @Test
    fun testBaseException() {
        val ex = Exception("")
        val status = GrpcErrorHandler.handle(ex)
        assertEquals(io.grpc.Status.INTERNAL.code, status.code)
    }
}