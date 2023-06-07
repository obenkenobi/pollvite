package com.pollvite.pollservice.services

import com.pollvite.grpc.poll.*
import com.pollvite.grpc.shared.IdPb
import com.pollvite.grpc.shared.TimestampsPb
import com.pollvite.pollservice.models.Audit
import com.pollvite.pollservice.models.PollChan
import com.pollvite.pollservice.models.Timestamps
import com.pollvite.pollservice.repositories.PollChanRepository
import com.pollvite.shared.errors.AppException
import com.pollvite.shared.errors.ErrorStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

interface PollChanService {
    fun createPollChan(pollCreatePb: Mono<PollChanCreatePb>) : Mono<PollChanReadPb>
    fun editPollChan(pollChanEditPb: Mono<PollChanEditPb>) : Mono<PollChanReadPb>
    fun getPollChanById(idPb: IdPb) : Mono<PollChanReadPb>
}

@Service
private class PollChanServiceImpl(@Autowired val pollChanRepository: PollChanRepository): PollChanService {

    override fun getPollChanById(idPb: IdPb) : Mono<PollChanReadPb> {
        return pollChanRepository.findById(idPb.value)
            .map { pollChanToReadDto(it) }
            .switchIfEmpty(Mono.error(AppException(ErrorStatus.NOT_FOUND, "Poll Channel Not Found")))
    }

    override fun createPollChan(pollCreatePb: Mono<PollChanCreatePb>) : Mono<PollChanReadPb> {
        return pollCreatePb.flatMap { pb ->
            val pollChan = PollChan(id = null,
                owner = pb.core.owner,
                description = pb.core.description,
                title = pb.core.title,
                timestamps = Timestamps.create(),
                audit = Audit(pb.core.owner, pb.core.owner)
            )
            return@flatMap pollChanRepository.save(pollChan)
        }.map { pollChanToReadDto(it) }

    }

    override fun editPollChan(pollChanEditPb: Mono<PollChanEditPb>) : Mono<PollChanReadPb> {
        return pollChanEditPb.flatMap { pb ->
            pollChanRepository.findById(pb.id)
                .map { oldPollChan -> Pair(pb, oldPollChan) }
        }.flatMap { pair ->
            val (pb, oldPollChan) = pair
            val pollChan = PollChan(id = oldPollChan.id,
                owner = pb.core.owner,
                description = pb.core.description,
                title = pb.core.title,
                timestamps = oldPollChan.timestamps.toUpdated(),
                audit = oldPollChan.audit.copy(updatedBy = pb.core.owner)
            )
            return@flatMap pollChanRepository.save(pollChan)
        }.map {
            pollChanToReadDto(it)
        }
    }

    fun pollChanToReadDto(pollChan: PollChan) : PollChanReadPb {
        return PollChanReadPb.newBuilder().also {
            it.id = pollChan.id
            it.core = PollChanCorePb.newBuilder().also { core ->
                core.owner = pollChan.owner
                core.description = pollChan.description
                core.title = pollChan.title
            }.build()
            it.timestamps = TimestampsPb.newBuilder().also { timestamps ->
                timestamps.createdAt = pollChan.timestamps.createdAt
                timestamps.updatedAt = pollChan.timestamps.updatedAt
            }.build()
        }.build()
    }
}