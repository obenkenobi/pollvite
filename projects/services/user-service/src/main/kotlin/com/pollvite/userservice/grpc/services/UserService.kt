package com.pollvite.userservice.grpc.services

import com.pollvite.grpc.shared.IdPb
import com.pollvite.grpc.user.*
import net.devh.boot.grpc.server.service.GrpcService
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@GrpcService
class UserService() : ReactorUserServiceGrpc.UserServiceImplBase() {

    override fun getUserProfileByUUID(request: Mono<IdPb>?): Mono<UserProfileFullPb> {
        return super.getUserProfileByUUID(request)
    }

    override fun getUserProfileByPublicId(request: Mono<IdPb>?): Mono<UserProfilePublicPb> {
        return super.getUserProfileByPublicId(request)
    }

    override fun getUserProfilesByPublicIds(request: Flux<IdPb>?): Flux<UserProfilePublicPb> {
        return super.getUserProfilesByPublicIds(request)
    }

    override fun createUserProfile(request: Mono<UserProfileCreatePb>?): Mono<UserProfileFullPb> {
        return super.createUserProfile(request)
    }

    override fun updateUserProfile(request: Mono<UserProfileUpdatePb>?): Mono<UserProfileFullPb> {
        return super.updateUserProfile(request)
    }

    override fun beginDeleteUserById(request: Mono<IdPb>?): Mono<UserProfileFullPb> {
        return super.beginDeleteUserById(request)
    }
}