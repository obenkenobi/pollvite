package com.pollvite.pollservice.procedures

import com.pollvite.grpc.poll.*
import com.pollvite.grpc.shared.IdPb
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
private class PollChanServiceImpl() : PollChanServiceGrpc.PollChanServiceImplBase() {
    @Override
    override fun getPollChanById(request: IdPb?, resObserver: StreamObserver<PollChanReadPb>?) {
        val value: PollChanReadPb = PollChanReadPb.newBuilder()
            .setId(request)
            .setCore(PollChanCorePb.newBuilder()
                .setOwner("Nero")
                .setDescription("food based polls")
                .setTitle("Food Polls")
                .build())
            .build()
        resObserver?.onNext(value)
        resObserver?.onCompleted();
    }

    override fun createPollChan(request: PollChanCreatePb?, resObserver: StreamObserver<PollChanReadPb>?) {
        val value = PollChanReadPb.newBuilder()
            .setCore(request?.core)
            .setId(IdPb.newBuilder()
                .setValue("someId")
                .build()
            ).build()
        resObserver?.onNext(value)
        resObserver?.onCompleted();
    }

    override fun editPollChan(request: PollChanEditPb?, resObserver: StreamObserver<PollChanReadPb>?) {
        val value = PollChanReadPb.newBuilder()
            .setCore(request?.core)
            .setId(IdPb.newBuilder()
                .setValue("someId")
                .build()
            ).build()
        resObserver?.onNext(value)
        resObserver?.onCompleted();
    }

    override fun deletePollChan(request: IdPb?, resObserver: StreamObserver<PollChanReadPb>?) {
        val value: PollChanReadPb = PollChanReadPb.newBuilder()
            .setId(request)
            .setCore(PollChanCorePb.newBuilder()
                .setOwner("Nero")
                .setDescription("food based polls")
                .setTitle("Food Polls")
                .build())
            .build()
        resObserver?.onNext(value)
        resObserver?.onCompleted();
    }
}