package com.pollvite.userservice.grpc.services

import com.pollvite.grpc.poll.ReactorPollChanServiceGrpc
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class UserService() : ReactorPollChanServiceGrpc.PollChanServiceImplBase() {
    // Todo: implement
}