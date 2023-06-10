package com.pollvite.polledgeservice.services

import com.pollvite.grpc.poll.PollChanAccessPb
import com.pollvite.grpc.poll.ReactorPollChanServiceGrpc.ReactorPollChanServiceStub
import com.pollvite.polledgeservice.dtos.PollChanCreateDto
import com.pollvite.polledgeservice.dtos.PollChanEditDto
import com.pollvite.polledgeservice.dtos.PollChanReadDto
import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

interface PollChanClientService {
    fun getPollChannelById(id: String): Mono<PollChanReadDto>
    fun createPollChannel(dtoSrc: Mono<PollChanCreateDto>): Mono<PollChanReadDto>
    fun editPollChannel(dtoSrc: Mono<PollChanEditDto>): Mono<PollChanReadDto>
    fun deletePollChannel(id: String): Mono<PollChanReadDto>
}

@Service
class PollChanClientServiceImpl(
    @Autowired @GrpcClient("pollService") private val pollChanServiceStub: ReactorPollChanServiceStub? = null,
    @Autowired private val securityService: SecurityService)
    : PollChanClientService {

    override fun getPollChannelById(id: String): Mono<PollChanReadDto> {
        return securityService.userPrincipal.flatMap { userPrincipal ->
            val pollChanAccessPb = PollChanAccessPb.newBuilder().also {
                it.id = id
                it.userId = userPrincipal.name
            }.build()
            pollChanServiceStub?.getPollChanById(pollChanAccessPb) ?: Mono.empty()
        }.map(PollChanReadDto::fromPb)
    }

    override fun createPollChannel(dtoSrc: Mono<PollChanCreateDto>): Mono<PollChanReadDto> {
        return Mono.zip(securityService.userPrincipal, dtoSrc).flatMap {
            val userPrincipal = it.t1
            val originalDto = it.t2
            val dto = originalDto.copy(core = originalDto.core?.copy(owner = userPrincipal.name))
            pollChanServiceStub?.createPollChan(dto.toPb()) ?: Mono.empty()
        }.map(PollChanReadDto::fromPb)
    }

    override fun editPollChannel(dtoSrc: Mono<PollChanEditDto>): Mono<PollChanReadDto> {
        return Mono.zip(securityService.userPrincipal, dtoSrc).flatMap {
            val userPrincipal = it.t1
            val originalDto = it.t2
            val dto = originalDto.copy(core = originalDto.core?.copy(owner = userPrincipal.name))
            pollChanServiceStub?.editPollChan(dto.toPb()) ?: Mono.empty()
        }.map(PollChanReadDto::fromPb)
    }

    override fun deletePollChannel(id: String): Mono<PollChanReadDto> {
        return securityService.userPrincipal.flatMap { userPrincipal ->
            val pollChanAccessPb = PollChanAccessPb.newBuilder().also {
                it.id = id
                it.userId = userPrincipal.name
            }.build()
            pollChanServiceStub?.deletePollChan(pollChanAccessPb) ?: Mono.empty()
        }.map(PollChanReadDto::fromPb)
    }

}