package com.pollvite.pollrestservice.dtos

import com.pollvite.grpc.poll.PollChanCorePb
import com.pollvite.grpc.poll.PollChanCreatePb
import com.pollvite.grpc.poll.PollChanEditPb
import com.pollvite.grpc.poll.PollChanReadPb
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class PollChanCoreDto(
    @field:NotBlank val owner: String?,
    @field:NotBlank val title: String?,
    @field:NotBlank val description: String?) {

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

data class PollChanReadDto(val id: String, val core: PollChanCoreDto) {

    companion object {
        fun fromPb(pb: PollChanReadPb): PollChanReadDto {
            return PollChanReadDto(
                id = pb.id,
                core = PollChanCoreDto.fromPb(pb.core)
            )
        }
    }

    fun toPb(): PollChanReadPb {
        return PollChanReadPb.newBuilder().also {
            it.id = id
            it.core = core.toPb()
        }.build()
    }
}

data class PollChanCreateDto(@field:Valid @field:NotNull val core: PollChanCoreDto?) {
    companion object {
        fun fromPb(pb: PollChanCreatePb): PollChanCreateDto {
            return PollChanCreateDto(
                core = PollChanCoreDto.fromPb(pb.core)
            )
        }
    }

    fun toPb(): PollChanCreatePb {
        return PollChanCreatePb.newBuilder().also {
            it.core = core?.toPb()
        }.build()
    }
}

data class PollChanEditDto(@field:Valid @field:NotNull val id: String?,
                           @field:Valid @field:NotNull val core: PollChanCoreDto?) {
    companion object {
        fun fromPb(pb: PollChanEditPb): PollChanEditDto {
            return PollChanEditDto(
                id = pb.id,
                core = PollChanCoreDto.fromPb(pb.core)
            )
        }
    }

    fun toPb(): PollChanEditPb {
        return PollChanEditPb.newBuilder().also {
            it.id = id
            it.core = core?.toPb()
        }.build()
    }
}