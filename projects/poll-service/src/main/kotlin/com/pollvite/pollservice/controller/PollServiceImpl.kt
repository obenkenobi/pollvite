package com.pollvite.pollservice.controller

import com.pollvite.grpc.poll.PollServiceGrpc
import net.devh.boot.grpc.server.service.GrpcService

//todo: resolve intellij classpath
@GrpcService
private class PollServiceImpl() : PollServiceGrpc.PollServiceImplBase() {
}