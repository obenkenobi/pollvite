package com.pollvite.shared.errors

import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

class ErrorStatusTest {
    @Test
    fun assertErrorStatusDescriptions() {
        val assertions = ErrorStatus.values().map { errorStatus ->
            Executable {
                val grpcStatus = errorStatus.toGrpcStatus()
                assertEquals(errorStatus.description, grpcStatus.description)
            }
        }
        assertAll("Asserting Error Status Descriptions", assertions)
    }

}