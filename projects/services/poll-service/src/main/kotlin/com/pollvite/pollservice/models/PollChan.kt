package com.pollvite.pollservice.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

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
        return this.copy(updatedAt=Instant.now().toEpochMilli())
    }
}

data class Audit(
    val createdBy: String,
    val updatedBy: String,
) {
    fun toUpdated(newUpdatedBy: String): Audit {
        return this.copy(updatedBy=newUpdatedBy)
    }
}

data class PollChanCore(val owner: String, val title: String, val description: String)

@Document
data class PollChan(
    @Id val id: String?,
    val core: PollChanCore,
    val timestamps: Timestamps,
    val audit: Audit,
)