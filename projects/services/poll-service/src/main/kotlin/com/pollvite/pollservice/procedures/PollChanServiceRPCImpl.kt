package com.pollvite.pollservice.procedures

import com.pollvite.grpc.poll.*
import com.pollvite.grpc.shared.IdPb
import com.pollvite.pollservice.services.PollChanService
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.beans.factory.annotation.Autowired
import reactor.core.publisher.Mono

@GrpcService
private class PollChanServiceRPCImpl(@Autowired val pollChanService: PollChanService) : ReactorPollChanServiceGrpc.PollChanServiceImplBase() {

    override fun getPollChanById(request: Mono<IdPb>) : Mono<PollChanReadPb> {
        return request.flatMap { getPollChanById(it) }
    }

    override fun getPollChanById(request:IdPb) : Mono<PollChanReadPb> {
        return pollChanService.getPollChanById(request)
    }

    override fun createPollChan(request: Mono<PollChanCreatePb>) : Mono<PollChanReadPb> {
        return pollChanService.createPollChan(request)
    }

    override fun editPollChan(request: Mono<PollChanEditPb>) : Mono<PollChanReadPb> {
        return pollChanService.editPollChan(request)
    }
}