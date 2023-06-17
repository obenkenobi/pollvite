package com.pollvite.shared.errors

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AppExceptionTest {

    @Test
    fun testWithDefaultMessageToGrpc() {
        val ex = AppException(ErrorStatus.BUSINESS_RULES_VIOLATION)
        assertEquals(ErrorStatus.BUSINESS_RULES_VIOLATION.description, ex.asGrpcStatus().description)
    }

    @Test
    fun testWithCustomMessageToGrpc() {
        val customMsg = "Custom Message"
        val ex = AppException(ErrorStatus.BUSINESS_RULES_VIOLATION, customMsg)
        assertEquals(customMsg, ex.asGrpcStatus().description)
    }
}