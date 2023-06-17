package com.pollvite.userservice.models

import com.pollvite.shared.models.embedded.Audit
import com.pollvite.shared.models.embedded.Timestamps
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "User")
data class PollChan(
    @Id val id: String?,
    val authId: String,
    val userName: String,
    val email: String,
    val timestamps: Timestamps,
    val audit: Audit,
)