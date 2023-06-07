package com.pollvite.pollservice.services

import com.pollvite.grpc.poll.*
import com.pollvite.grpc.shared.IdPb
import com.pollvite.pollservice.mappers.PollChanMapper
import com.pollvite.pollservice.models.embedded.Audit
import com.pollvite.pollservice.models.PollChan
import com.pollvite.pollservice.models.embedded.Timestamps
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

    override fun getPollChanById(idPb: IdPb) : Mono<PollChanReadPb> = pollChanRepository.findById(idPb.value)
        .map { PollChanMapper.modelToReadPb(it) }
        .switchIfEmpty(Mono.error(AppException(ErrorStatus.NOT_FOUND)))

    override fun createPollChan(pollCreatePb: Mono<PollChanCreatePb>) : Mono<PollChanReadPb> = pollCreatePb
        .flatMap { pb ->
            val pollChan = PollChan(id = null,
                core = PollChanMapper.corePbToCoreModel(pb.core),
                timestamps = Timestamps.create(),
                audit = Audit(pb.core.owner, pb.core.owner)
            )
            return@flatMap pollChanRepository.save(pollChan)
        }.map { PollChanMapper.modelToReadPb(it) }

    override fun editPollChan(pollChanEditPb: Mono<PollChanEditPb>) : Mono<PollChanReadPb> = pollChanEditPb
        .flatMap { pb ->
            pollChanRepository.findById(pb.id).map { oldPollChan -> Pair(pb, oldPollChan) }
        }.flatMap {
            val (pb, oldPollChan) = it
            val pollChan = PollChan(id = oldPollChan.id,
                core = PollChanMapper.corePbToCoreModel(pb.core),
                timestamps = oldPollChan.timestamps.toUpdated(),
                audit = oldPollChan.audit.copy(updatedBy = pb.core.owner)
            )
            return@flatMap pollChanRepository.save(pollChan)
        }.map {
            PollChanMapper.modelToReadPb(it)
    }
}