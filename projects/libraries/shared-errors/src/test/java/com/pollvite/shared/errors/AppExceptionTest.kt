package com.pollvite.shared.errors

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AppExceptionTest {
    @Test
    fun testWithCustomMessageToGrpc() {
        val customMsg = "Custom Message"
        val ex = AppException(ErrorStatus.BUSINESS_RULES_VIOLATION, customMsg)
        assertEquals(customMsg, ex.asGrpcStatus().description)
    }
}