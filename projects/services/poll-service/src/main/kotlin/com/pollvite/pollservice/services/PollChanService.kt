package com.pollvite.pollservice.services

import com.pollvite.grpc.poll.*
import com.pollvite.grpc.shared.IdPb
import com.pollvite.pollservice.mappers.PollChanMapper
import com.pollvite.pollservice.models.PollChan
import com.pollvite.pollservice.repositories.PollChanRepository
import com.pollvite.shared.errors.AppException
import com.pollvite.shared.errors.ErrorStatus
import com.pollvite.shared.models.embedded.Audit
import com.pollvite.shared.models.embedded.Timestamps
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

interface PollChanService {
    fun createPollChan(pollCreatePb: Mono<PollChanCreatePb>) : Mono<PollChanReadPb>
    fun editPollChan(pollChanEditPb: Mono<PollChanEditPb>) : Mono<PollChanReadPb>
    fun getPollChanById(pollChanAccessPb: Mono<PollChanAccessPb>) : Mono<PollChanReadPb>
    fun deletePollChan(pollChanAccessPb: Mono<PollChanAccessPb>): Mono<PollChanReadPb>
}

@Service
private class PollChanServiceImpl(@Autowired private val pollChanRepository: PollChanRepository): PollChanService {

    override fun getPollChanById(pollChanAccessPb: Mono<PollChanAccessPb>) : Mono<PollChanReadPb> {
        return pollChanAccessPb.flatMap { accessPb -> pollChanRepository.findById(accessPb.id) }
            .map { PollChanMapper.modelToReadPb(it) }
            .switchIfEmpty(Mono.error(AppException(ErrorStatus.NOT_FOUND)))
    }

    override fun createPollChan(pollCreatePb: Mono<PollChanCreatePb>) : Mono<PollChanReadPb> = pollCreatePb
        .flatMap { pb ->
            val pollChan = PollChan(id = null,
                core = PollChanMapper.corePbToCoreModel(pb.core),
                timestamps = Timestamps.create(),
                audit = Audit(pb.core.owner, pb.core.owner)
            )
            pollChanRepository.save(pollChan)
        }.map { PollChanMapper.modelToReadPb(it) }

    override fun editPollChan(pollChanEditPb: Mono<PollChanEditPb>) : Mono<PollChanReadPb> {
        return pollChanEditPb.flatMap { pb ->
                pollChanRepository.findById(pb.id).map { oldPollChan -> Pair(pb, oldPollChan) }
            }.flatMap {
                val (pb, oldPollChan) = it
                val pollChan = PollChan(
                    id = oldPollChan.id,
                    core = PollChanMapper.corePbToCoreModel(pb.core),
                    timestamps = oldPollChan.timestamps.toUpdated(),
                    audit = oldPollChan.audit.copy(updatedBy = pb.core.owner)
                )
                pollChanRepository.save(pollChan)
            }.map {
                PollChanMapper.modelToReadPb(it)
            }
    }

    override fun deletePollChan(pollChanAccessPb: Mono<PollChanAccessPb>): Mono<PollChanReadPb> {
        return pollChanAccessPb.flatMap { accessPb ->
            pollChanRepository.findById(accessPb.id)
                .map { existingPollChan -> Pair(accessPb, existingPollChan) }
        }.switchIfEmpty(
            Mono.error(AppException(ErrorStatus.NOT_FOUND))
        ).flatMap {
            val (accessPb, existingPollChan) = it
            if (accessPb.userId != existingPollChan.core.owner) {
                return@flatMap Mono.error(AppException(ErrorStatus.BR_VIOLATION, "Not a poll owner"))
            }
            pollChanRepository.deleteById(accessPb.id)
                .then(Mono.just(existingPollChan))
        }.map { existingPollChan ->
            PollChanMapper.modelToReadPb(existingPollChan)
        }
    }

}