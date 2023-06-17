package com.pollvite.userservice.models

import com.pollvite.shared.models.embedded.Audit
import com.pollvite.shared.models.embedded.Timestamps
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "User")
data class PollChan(
    @Id val publicId: String?,
    @Indexed(unique = true) val uuid: String,
    val userName: String,
    val email: String,
    val timestamps: Timestamps,
    val audit: Audit,
)