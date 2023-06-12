package com.pollvite.shared.models.embedded

/**
 * Embedded immutable POJO containing audit fields for a data persistence model.
 * @param createdBy creator of the data persistence model instance.
 * @param updatedBy editor of the data persistence model instance.
 * */
data class Audit(
    val createdBy: String,
    val updatedBy: String,
) {
    /**
     * @param newUpdatedBy new updater for the data persistence model instance.
     * @return A new Audit instance
     * */
    fun toUpdated(newUpdatedBy: String): Audit {
        return this.copy(updatedBy=newUpdatedBy)
    }
}