package com.pollvite.polledgeservice.dtos

import com.pollvite.grpc.poll.PollChanCorePb
import com.pollvite.grpc.poll.PollChanCreatePb
import com.pollvite.grpc.poll.PollChanEditPb
import com.pollvite.grpc.poll.PollChanPagePb
import com.pollvite.grpc.poll.PollChanReadPb
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class PollChanCoreDto(
    val owner: String?,
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

data class PollChanPageDto(val content: List<PollChanReadDto>, val total: Long) {
    companion object {
        fun fromPb(pb: PollChanPagePb) : PollChanPageDto {
            return PollChanPageDto(
                total = pb.total,
                content = pb.contentList.map { PollChanReadDto.fromPb(it) }
            )
        }
    }

    fun toPb(): PollChanPagePb {
        return PollChanPagePb.newBuilder().also { builder ->
            builder.total = total
            builder.addAllContent(content.map { it.toPb() })
        }.build()
    }
}