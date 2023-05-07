package com.pollvite.pollrestservice.services

import com.pollvite.grpc.poll.PollChanServiceGrpc.PollChanServiceStub
import com.pollvite.pollrestservice.dtos.IdDto
import com.pollvite.pollrestservice.dtos.PollChanCreateDto
import com.pollvite.pollrestservice.dtos.PollChanEditDto
import com.pollvite.pollrestservice.dtos.PollChanReadDto
import com.pollvite.pollrestservice.reactive.ReactiveGrpc
import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

interface PollChanRpcService {
    fun getPollChannelById(id: String): Mono<PollChanReadDto>
    fun createPollChannel(dtoSrc: Mono<PollChanCreateDto>): Mono<PollChanReadDto>
    fun editPollChannel(dtoSrc: Mono<PollChanEditDto>): Mono<PollChanReadDto>
    fun deletePollChannel(id: String): Mono<PollChanReadDto>
}

@Service
class PollChanRpcServiceImpl(
    @Autowired @GrpcClient("pollService") private val pollChanServiceClient: PollChanServiceStub? = null)
    : PollChanRpcService {

    override fun getPollChannelById(id: String): Mono<PollChanReadDto> {
        val idPb = IdDto(id).toPb()
        return ReactiveGrpc.callMono(pollChanServiceClient!!::getPollChanById, idPb)
            .map(PollChanReadDto::fromPb)
    }

    override fun createPollChannel(dtoSrc: Mono<PollChanCreateDto>): Mono<PollChanReadDto> {
        return dtoSrc.flatMap { dto ->
            ReactiveGrpc.callMono(pollChanServiceClient!!::createPollChan, dto.toPb())
        }.map(PollChanReadDto::fromPb)
    }

    override fun editPollChannel(dtoSrc: Mono<PollChanEditDto>): Mono<PollChanReadDto> {
        return dtoSrc.flatMap { dto ->
            ReactiveGrpc.callMono(pollChanServiceClient!!::editPollChan, dto.toPb())
        }.map(PollChanReadDto::fromPb)
    }

    override fun deletePollChannel(id: String): Mono<PollChanReadDto> {
        val idPb = IdDto(id).toPb()
        return ReactiveGrpc.callMono(pollChanServiceClient!!::deletePollChan, idPb)
            .map(PollChanReadDto::fromPb)
    }

}