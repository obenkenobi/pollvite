package com.pollvite.identityservice.grpc.services

import com.pollvite.grpc.user.ReactorIdentityServiceGrpc
import com.pollvite.identityservice.services.UserService
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.beans.factory.annotation.Autowired

@GrpcService
class IdentityServiceGrpcImpl(@Autowired val userService: UserService)
    : ReactorIdentityServiceGrpc.IdentityServiceImplBase() {
}