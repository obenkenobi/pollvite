package com.pollvite.shared.models.embedded

import java.time.Instant

/**
 * Embedded immutable POJO containing timestamp fields for a data persistence model.
 * @param createdAt When the data persistence model instance was created in UTC time measured in milliseconds.
 * @param updatedAt When the data persistence model instance was updated in UTC time measured in milliseconds.
 * */
data class Timestamps(
    val createdAt: Long,
    val updatedAt: Long,
) {
    companion object {
        /**
         * @return A [Timestamps] instance where [createdAt] and the [updatedAt] are the current timestamp.
         * */
        fun create(): Timestamps {
            val current = Instant.now().toEpochMilli()
            return Timestamps(
                createdAt = current, updatedAt = current)
        }
    }
    fun toUpdated(): Timestamps {
        return this.copy(updatedAt= Instant.now().toEpochMilli())
    }
}
