package com.pollvite.pollservice.grpc.services

import com.pollvite.grpc.poll.*
import com.pollvite.pollservice.services.PollChanService
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.beans.factory.annotation.Autowired
import reactor.core.publisher.Mono

@GrpcService
private class PollChanServiceGrpcImpl(@Autowired private val pollChanService: PollChanService)
    : ReactorPollChanServiceGrpc.PollChanServiceImplBase() {

    override fun getPollChanById(request: Mono<PollChanAccessPb>) : Mono<PollChanReadPb> {
        return request.map { pollChanService.getPollChanById(it) }
    }

    override fun getPollChanPage(request: Mono<PollChanPageFilterPb>): Mono<PollChanPagePb> {
        return request.map { pollChanService.getPollChanPage(it) }
    }

    override fun createPollChan(request: Mono<PollChanCreatePb>) : Mono<PollChanReadPb> {
        return request.map { pollChanService.createPollChan(it) }
    }

    override fun editPollChan(request: Mono<PollChanEditPb>) : Mono<PollChanReadPb> {
        return request.map { pollChanService.editPollChan(it) }
    }

    override fun deletePollChan(request: Mono<PollChanAccessPb>): Mono<PollChanReadPb> {
        return request.map { pollChanService.deletePollChan(it) }
    }
}