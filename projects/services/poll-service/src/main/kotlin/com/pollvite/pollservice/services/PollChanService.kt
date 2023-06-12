package com.pollvite.pollservice.services

import com.pollvite.grpc.poll.*
import com.pollvite.pollservice.services.businessrules.PollChanBrService
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
    fun getPollChanPage(pollChanPageFilterPb: Mono<PollChanPageFilterPb>?): Mono<PollChanPagePb>
    fun editPollChan(pollChanEditPb: Mono<PollChanEditPb>) : Mono<PollChanReadPb>
    fun getPollChanById(pollChanAccessPb: Mono<PollChanAccessPb>) : Mono<PollChanReadPb>
    fun deletePollChan(pollChanAccessPb: Mono<PollChanAccessPb>): Mono<PollChanReadPb>
}

@Service
private class PollChanServiceImpl(@Autowired private val pollChanRepository: PollChanRepository,
                                  @Autowired private val pollChanBrs: PollChanBrService): PollChanService {

    override fun getPollChanById(pollChanAccessPb: Mono<PollChanAccessPb>) : Mono<PollChanReadPb> {
        return pollChanAccessPb.flatMap { accessPb -> getPollChanByIdRequired(accessPb.id) }
            .map { PollChanMapper.modelToReadPb(it) }
    }

    override fun getPollChanPage(pollChanPageFilterPb: Mono<PollChanPageFilterPb>?): Mono<PollChanPagePb> {
        TODO("Not yet implemented")
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
            getPollChanByIdRequired(pb.id)
                .map { oldPollChan -> Pair(pb, oldPollChan) }
        }.flatMap {
            val (pb, existingPollChan) = it
            pollChanBrs.validateEditPollChan(pb, existingPollChan).ifPassOrFail(
                passBlock = {
                    val pollChan = PollChan(
                        id = existingPollChan.id,
                        core = PollChanMapper.corePbToCoreModel(pb.core),
                        timestamps = existingPollChan.timestamps.toUpdated(),
                        audit = existingPollChan.audit.copy(updatedBy = pb.core.owner)
                    )
                    pollChanRepository.save(pollChan) },
                failBlock = { ex -> Mono.error(ex) })
        }.map {
            PollChanMapper.modelToReadPb(it)
        }
    }

    override fun deletePollChan(pollChanAccessPb: Mono<PollChanAccessPb>): Mono<PollChanReadPb> {
        return pollChanAccessPb.flatMap { accessPb ->
            getPollChanByIdRequired(accessPb.id)
                .map { existingPollChan -> Pair(accessPb, existingPollChan) }
        }.flatMap {
            val (accessPb, existingPollChan) = it
            pollChanBrs.validateDeletePollChan(accessPb, existingPollChan)
                .ifPassOrFail(
                    passBlock = { pollChanRepository.deleteById(accessPb.id) },
                    failBlock = { ex -> Mono.error(ex) }
                ).thenReturn(existingPollChan)
        }.map { existingPollChan ->
            PollChanMapper.modelToReadPb(existingPollChan)
        }
    }

    private fun getPollChanByIdRequired(id: String): Mono<PollChan> = pollChanRepository.findById(id)
        .switchIfEmpty(Mono.error(AppException(ErrorStatus.NOT_FOUND)))

}