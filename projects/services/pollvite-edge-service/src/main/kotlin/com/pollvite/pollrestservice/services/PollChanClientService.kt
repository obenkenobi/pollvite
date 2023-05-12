package com.pollvite.pollrestservice.services

import com.pollvite.grpc.poll.ReactorPollChanServiceGrpc.ReactorPollChanServiceStub
import com.pollvite.pollrestservice.dtos.IdDto
import com.pollvite.pollrestservice.dtos.PollChanCreateDto
import com.pollvite.pollrestservice.dtos.PollChanEditDto
import com.pollvite.pollrestservice.dtos.PollChanReadDto
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
    @Autowired @GrpcClient("pollService") private val pollChanServiceStub: ReactorPollChanServiceStub? = null)
    : PollChanClientService {

    override fun getPollChannelById(id: String): Mono<PollChanReadDto> {
        val idPb = IdDto(id).toPb()
        return pollChanServiceStub?.getPollChanById(idPb)
            ?.map(PollChanReadDto::fromPb) ?: Mono.empty()
    }

    override fun createPollChannel(dtoSrc: Mono<PollChanCreateDto>): Mono<PollChanReadDto> {
        return dtoSrc.flatMap { dto ->
            pollChanServiceStub?.createPollChan(dto.toPb()) ?: Mono.empty()
        }.map(PollChanReadDto::fromPb)
    }

    override fun editPollChannel(dtoSrc: Mono<PollChanEditDto>): Mono<PollChanReadDto> {
        return dtoSrc.flatMap { dto ->
            pollChanServiceStub?.editPollChan(dto.toPb()) ?: Mono.empty()
        }.map(PollChanReadDto::fromPb)
    }

    override fun deletePollChannel(id: String): Mono<PollChanReadDto> {
        val idPb = IdDto(id).toPb()
        return pollChanServiceStub?.deletePollChan(idPb)
            ?.map(PollChanReadDto::fromPb) ?: Mono.empty()
    }

}