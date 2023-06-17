package com.pollvite.pollservice.models.embedded

import org.springframework.data.mongodb.core.index.Indexed

data class PollChanCore(
    val owner: String,
    @Indexed(unique = true) val titleId: String,
    val description: String)