package com.pollvite.pollservice.utils

import java.util.*

object PollUtils {
    private val whiteSpacesRegex = "\\s+".toRegex()
    private val nonAlphanumericWhiteSpaceDashRegex = "[^a-zA-Z0-9\\s-]".toRegex()

    fun titleToId(title: String) : String {
        return title.lowercase()
            .replace(nonAlphanumericWhiteSpaceDashRegex, "")
            .replace("-", " ")
            .trim()
            .replace(whiteSpacesRegex, "-")
    }

    fun idToTitle(id: String) : String {
        return id.split("-").joinToString(" ") { s ->
            s.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        }
    }
}