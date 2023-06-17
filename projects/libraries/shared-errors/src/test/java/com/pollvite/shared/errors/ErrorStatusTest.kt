package com.pollvite.shared.errors

import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

class ErrorStatusTest {
    @Test
    fun testErrorStatusGrpcDescriptions() {
        val assertions = ErrorStatus.values().map { errorStatus ->
            Executable {
                val grpcStatus = errorStatus.toGrpcStatus()
                assertEquals(errorStatus.description, grpcStatus.description)
            }
        }
        assertAll("Asserting Error Status Descriptions", assertions)
    }

    @Test
    fun testErrorStatusGrpcCodes() {
        assertAll("Asserting Error Status Codes",
            { assertEquals(io.grpc.Status.INVALID_ARGUMENT.code, ErrorStatus.CLIENT.toGrpcStatus().code) },
            { assertEquals(io.grpc.Status.INVALID_ARGUMENT.code,
                ErrorStatus.BUSINESS_RULES_VIOLATION.toGrpcStatus().code) },
            { assertEquals(io.grpc.Status.NOT_FOUND.code, ErrorStatus.NOT_FOUND.toGrpcStatus().code) },
            { assertEquals(io.grpc.Status.UNAUTHENTICATED.code, ErrorStatus.UNAUTHENTICATED.toGrpcStatus().code) },
            { assertEquals(io.grpc.Status.PERMISSION_DENIED.code, ErrorStatus.UNAUTHORIZED.toGrpcStatus().code) },
            { assertEquals(io.grpc.Status.INTERNAL.code, ErrorStatus.INTERNAL.toGrpcStatus().code) },
        )
    }

}