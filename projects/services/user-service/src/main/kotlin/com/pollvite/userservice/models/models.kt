package com.pollvite.userservice.models

import com.pollvite.shared.models.embedded.Audit
import com.pollvite.shared.models.embedded.Timestamps
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "UserProfile")
data class UserProfile(
    @Id val publicId: String?,
    @Indexed(unique = true) val uuid: String,
    @Indexed(unique = true) val userName: String,
    val timestamps: Timestamps,
    val batchIndex: Long,
    val fbSynced: Boolean = false,
    val action: Action = Action.NONE
) {
    enum class Action {NONE, CREATED, UPDATE, DELETE}
}