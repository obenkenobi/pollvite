package com.pollvite.pollservice.utils

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PollUtilsTest {
    @Test
    fun testTitleToIdentifierConversions() {
        val originalTitle = "Hello -  .!!- Big World !!!"
        val identifier = PollUtils.titleToId(originalTitle)
        val newTitle = PollUtils.idToTitle(identifier)
        assertEquals("hello-big-world", identifier, "Failed to convert title to newTitle")
        assertEquals("Hello Big World", newTitle, "Failed to convert identifier to title")
    }
}