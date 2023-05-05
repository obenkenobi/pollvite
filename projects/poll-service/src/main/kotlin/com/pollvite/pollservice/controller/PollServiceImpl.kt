package com.pollvite.pollservice.controller

import com.pollvite.grpc.poll.*
import com.pollvite.grpc.shared.IdDto
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService

//todo: resolve intellij classpath
@GrpcService
private class PollServiceImpl() : PollServiceGrpc.PollServiceImplBase() {
    @Override
    override fun getPollChannelById(request: IdDto?, responseObserver: StreamObserver<PollChannelReadDto>?) {
        val dto: PollChannelReadDto = PollChannelReadDto.newBuilder()
            .setId(request)
            .setPollChannelCore(PollChannelDto.newBuilder()
                .setOwner("Nero")
                .setDescription("food based polls")
                .setTitle("Food Polls")
                .build())
            .build()
        responseObserver?.onNext(dto)
        responseObserver?.onCompleted();
    }

    override fun createPollChannel(
        request: PollChannelCreateDto?,
        responseObserver: StreamObserver<PollChannelReadDto>?
    ) {
        val dto = PollChannelReadDto.newBuilder()
            .setPollChannelCore(request?.pollChannelCore)
            .setId(IdDto.newBuilder()
                .setId("someId")
                .build()
            ).build()
        responseObserver?.onNext(dto)
        responseObserver?.onCompleted();
    }

    override fun editPollChannel(request: PollChannelEditDto?, responseObserver: StreamObserver<PollChannelReadDto>?) {
        val dto = PollChannelReadDto.newBuilder()
            .setPollChannelCore(request?.pollChannelCore)
            .setId(IdDto.newBuilder()
                .setId("someId")
                .build()
            ).build()
        responseObserver?.onNext(dto)
        responseObserver?.onCompleted();
    }

    override fun deletePollChannel(request: IdDto?, responseObserver: StreamObserver<PollChannelReadDto>?) {
        val dto: PollChannelReadDto = PollChannelReadDto.newBuilder()
            .setId(request)
            .setPollChannelCore(PollChannelDto.newBuilder()
                .setOwner("Nero")
                .setDescription("food based polls")
                .setTitle("Food Polls")
                .build())
            .build()
        responseObserver?.onNext(dto)
        responseObserver?.onCompleted();
    }
}