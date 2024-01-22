package com.pollvite.identityservice.grpc.services

import com.pollvite.grpc.shared.IdPb
import com.pollvite.grpc.user.*
import com.pollvite.identityservice.services.UserService
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.beans.factory.annotation.Autowired
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@GrpcService
class UserServiceGrpcImpl(@Autowired val userService: UserService) : ReactorIdentityServiceGrpc.IdentityServiceImplBase() {

    override fun getUserProfileByUUID(request: Mono<IdPb>): Mono<UserProfileFullPb> {
        return request.map { userService.getUserProfileByUUID(it.value) }
    }

    override fun getUserProfileByPublicId(request: Mono<IdPb>): Mono<UserProfilePublicPb> {
        return request.map { userService.getUserProfileByPublicId(it.value) }
    }

    override fun getUserProfilesByPublicIds(request: Flux<IdPb>): Flux<UserProfilePublicPb> {
        return request.map { it.value }
            .collectList()
            .map { userService.getUserProfileByPublicIds(it) }
            .flatMapIterable { it }
    }

    override fun createUserProfile(request: Mono<UserProfileCreatePb>): Mono<UserProfileFullPb> {
        return request.map { userService.createUserProfile(it) }
    }

    override fun updateUserProfile(request: Mono<UserProfileUpdatePb>): Mono<UserProfileFullPb> {
        return request.map { userService.updateUserProfile(it) }
    }

    override fun beginDeleteUserByUUID(request: Mono<IdPb>): Mono<UserProfileFullPb> {
        return request.map { userService.beginDeleteUserByUUID(it.value) }
    }
}