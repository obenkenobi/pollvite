package com.pollvite.pollservice.models

import com.pollvite.pollservice.models.embedded.Audit
import com.pollvite.pollservice.models.embedded.PollChanCore
import com.pollvite.pollservice.models.embedded.Timestamps
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "PollChan")
data class PollChan(
    @Id val id: String?,
    val core: PollChanCore,
    val timestamps: Timestamps,
    val audit: Audit,
)