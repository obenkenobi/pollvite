package com.pollvite.pollrestservice.dtos

import com.pollvite.grpc.poll.PollChanCorePb
import com.pollvite.grpc.poll.PollChanCreatePb
import com.pollvite.grpc.poll.PollChanReadPb

data class PollChanCoreDto(
    val owner: String,
    val title: String,
    val description: String) {
    companion object {
        fun fromPb(pb: PollChanCorePb): PollChanCoreDto {
            return PollChanCoreDto(
                owner = pb.owner,
                title = pb.title,
                description = pb.description);
        }
    }

    fun toPb() : PollChanCorePb {
        return PollChanCorePb.newBuilder().also {
            it.owner = owner;
            it.title = title;
            it.description = description;
        }.build()
    }
}

data class PollChanReadDto(val id: IdDto, val core: PollChanCoreDto) {
    companion object {
        fun fromPb(pb: PollChanReadPb): PollChanReadDto {
            return PollChanReadDto(
                id = IdDto.fromPb(pb.id),
                core = PollChanCoreDto.fromPb(pb.core)
            )
        }
    }

    fun toPb(): PollChanReadPb {
        return PollChanReadPb.newBuilder().also {
            it.id = id.toPb()
            it.core = core.toPb()
        }.build()
    }
}

data class PollChanCreateDto(val core: PollChanCoreDto) {
    companion object {
        fun fromPb(pb: PollChanCreatePb): PollChanCreateDto {
            return PollChanCreateDto(
                core = PollChanCoreDto.fromPb(pb.core)
            )
        }
    }

    fun toPb(): PollChanCreatePb {
        return PollChanCreatePb.newBuilder().also {
            it.core = core.toPb()
        }.build()
    }
}