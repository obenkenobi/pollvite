package com.pollvite.pollservice.mappers

import com.pollvite.grpc.poll.PollChanCorePb
import com.pollvite.grpc.poll.PollChanReadPb
import com.pollvite.grpc.shared.TimestampsPb
import com.pollvite.pollservice.models.PollChan
import com.pollvite.pollservice.models.embedded.PollChanCore

object PollChanMapper {

    fun corePbToCoreModel(corePb: PollChanCorePb): PollChanCore = PollChanCore(
        owner = corePb.owner,
        description = corePb.description,
        title = corePb.title,
    )

    private fun coreModelToCorePb(core: PollChanCore): PollChanCorePb = PollChanCorePb.newBuilder().also {
        it.owner = core.owner
        it.description = core.description
        it.title = core.title
    }.build()

    fun modelToReadPb(pollChan: PollChan) : PollChanReadPb = PollChanReadPb.newBuilder().also {
        it.id = pollChan.id
        it.core = coreModelToCorePb(pollChan.core)
        it.timestamps = TimestampsPb.newBuilder().also { timestamps ->
            timestamps.createdAt = pollChan.timestamps.createdAt
            timestamps.updatedAt = pollChan.timestamps.updatedAt
        }.build()
    }.build()

}