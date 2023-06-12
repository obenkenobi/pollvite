package com.pollvite.shared.models.embedded

import java.time.Instant

/**
 * Embedded immutable POJO containing timestamp fields for a data persistence model.
 * @param createdAt when the data persistence model instance was created
 * @param updatedAt when the data persistence model instance was updated
 * */
data class Timestamps(
    val createdAt: Long,
    val updatedAt: Long,
) {
    companion object {
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
