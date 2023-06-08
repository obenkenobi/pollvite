package com.pollvite.shared.models.embedded

data class Audit(
    val createdBy: String,
    val updatedBy: String,
) {
    fun toUpdated(newUpdatedBy: String): Audit {
        return this.copy(updatedBy=newUpdatedBy)
    }
}