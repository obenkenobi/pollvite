package com.pollvite.pollservice.procedures

import com.pollvite.grpc.poll.*
import com.pollvite.grpc.shared.IdPb
import net.devh.boot.grpc.server.service.GrpcService
import reactor.core.publisher.Mono

@GrpcService
private class PollChanServiceImpl() : ReactorPollChanServiceGrpc.PollChanServiceImplBase() {
    @Override
    override fun getPollChanById(requst: Mono<IdPb>) : Mono<PollChanReadPb> {
        return requst.map { req ->
            PollChanReadPb.newBuilder().also {
                it.id = req?.value
                it.core = PollChanCorePb.newBuilder().also { core ->
                    core.owner = "Nero"
                    core.description = "food based polls"
                    core.title = "Food Polls"
                }.build()
            }.build()
        }
    }

    override fun createPollChan(request: Mono<PollChanCreatePb>) : Mono<PollChanReadPb> {
        return request.map { req ->
            PollChanReadPb.newBuilder().apply {
                id = "someId"
                core = req?.core
            }.build()
        }
    }

    override fun editPollChan(request: Mono<PollChanEditPb>) : Mono<PollChanReadPb> {
        return request.map { req ->
            PollChanReadPb.newBuilder().apply {
                id = req?.id
                core = req?.core
            }.build()
        }
    }

    override fun getPollChanById(requst:IdPb) : Mono<PollChanReadPb> {
        val value = PollChanReadPb.newBuilder().also {
            it.id = requst?.value
            it.core = PollChanCorePb.newBuilder().also { core ->
                core.owner = "Nero"
                core.description = "food based polls"
                core.title = "Food Polls"
            }.build()
        }.build()
        return Mono.just(value)
    }
}