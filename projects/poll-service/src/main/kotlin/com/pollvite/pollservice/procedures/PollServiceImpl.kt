package com.pollvite.pollservice.procedures

import com.pollvite.grpc.poll.*
import com.pollvite.grpc.shared.IdDto
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
private class PollServiceImpl() : PollChanServiceGrpc.PollChanServiceImplBase() {
    @Override
    override fun getPollChanById(request: IdDto?, resObserver: StreamObserver<PollChanReadDto>?) {
        val dto: PollChanReadDto = PollChanReadDto.newBuilder()
            .setId(request)
            .setCore(PollChanDto.newBuilder()
                .setOwner("Nero")
                .setDescription("food based polls")
                .setTitle("Food Polls")
                .build())
            .build()
        resObserver?.onNext(dto)
        resObserver?.onCompleted();
    }

    override fun createPollChan(request: PollChanCreateDto?, resObserver: StreamObserver<PollChanReadDto>?) {
        val dto = PollChanReadDto.newBuilder()
            .setCore(request?.core)
            .setId(IdDto.newBuilder()
                .setValue("someId")
                .build()
            ).build()
        resObserver?.onNext(dto)
        resObserver?.onCompleted();
    }

    override fun editPollChan(request: PollChanEditDto?, resObserver: StreamObserver<PollChanReadDto>?) {
        val dto = PollChanReadDto.newBuilder()
            .setCore(request?.core)
            .setId(IdDto.newBuilder()
                .setValue("someId")
                .build()
            ).build()
        resObserver?.onNext(dto)
        resObserver?.onCompleted();
    }

    override fun deletePollChan(request: IdDto?, resObserver: StreamObserver<PollChanReadDto>?) {
        val dto: PollChanReadDto = PollChanReadDto.newBuilder()
            .setId(request)
            .setCore(PollChanDto.newBuilder()
                .setOwner("Nero")
                .setDescription("food based polls")
                .setTitle("Food Polls")
                .build())
            .build()
        resObserver?.onNext(dto)
        resObserver?.onCompleted();
    }
}