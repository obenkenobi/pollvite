package com.pollvite.shared.models.embedded

/**
 * Embedded immutable class containing audit fields for a data persistence model.
 * */
data class Audit(
    val createdBy: String,
    val updatedBy: String,
) {
    fun toUpdated(newUpdatedBy: String): Audit {
        return this.copy(updatedBy=newUpdatedBy)
    }
}