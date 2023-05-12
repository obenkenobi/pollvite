package com.pollvite.pollservice.procedures

import com.pollvite.grpc.poll.*
import com.pollvite.grpc.shared.IdPb
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
private class PollChanServiceImpl() : PollChanServiceGrpc.PollChanServiceImplBase() {
    @Override
    override fun getPollChanById(request: IdPb?, resObserver: StreamObserver<PollChanReadPb>?) {
        val value: PollChanReadPb = PollChanReadPb.newBuilder().also {
            it.id = request?.value
            it.core = PollChanCorePb.newBuilder().also { core ->
                core.owner = "Nero"
                core.description = "food based polls"
                core.title = "Food Polls"
            }.build()
        }.build()
        resObserver?.onNext(value)
        resObserver?.onCompleted();
    }

    override fun createPollChan(request: PollChanCreatePb?, resObserver: StreamObserver<PollChanReadPb>?) {
        val value = PollChanReadPb.newBuilder().apply {
            id = "someId"
            core = request?.core
        }.build()
        resObserver?.onNext(value)
        resObserver?.onCompleted();
    }

    override fun editPollChan(request: PollChanEditPb?, resObserver: StreamObserver<PollChanReadPb>?) {
        val value = PollChanReadPb.newBuilder().apply {
            core = request?.core
            id = request?.id
        }.build()
        resObserver?.onNext(value)
        resObserver?.onCompleted();
    }

    override fun deletePollChan(request: IdPb?, resObserver: StreamObserver<PollChanReadPb>?) {
        val value: PollChanReadPb = PollChanReadPb.newBuilder().also {
            it.id = request?.value
            it.core = PollChanCorePb.newBuilder().also { core ->
                core.owner = "Nero"
                core.description = "food based polls"
                core.title = "Food Polls"
            }.build()
        }.build()
        resObserver?.onNext(value)
        resObserver?.onCompleted();
    }
}