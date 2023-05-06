package com.pollvite.pollrestservice.services

import com.pollvite.grpc.poll.PollChanCreatePb
import com.pollvite.grpc.poll.PollChanReadPb
import com.pollvite.grpc.poll.PollChanServiceGrpc.PollChanServiceStub
import com.pollvite.grpc.shared.IdPb
import com.pollvite.pollrestservice.dtos.PollChanReadDto
import com.pollvite.pollrestservice.grpcutils.GrpcMonoObserver
import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono


@Service
class PollChanRpcService(
    @Autowired @GrpcClient("pollService") private val pollChanService: PollChanServiceStub? = null) {

    fun getPollChannelById(id: String): Mono<PollChanReadDto> {
        return Mono.create { it ->
            val idPb = IdPb.newBuilder().also { it.value = id }.build()
            pollChanService?.getPollChanById(idPb, GrpcMonoObserver(it))
        }.map {
            PollChanReadDto.fromPb(it)
        }
    }

    fun createPollChannel(Pb: PollChanCreatePb): Mono<PollChanReadDto> {
        return Mono.create {
            pollChanService?.createPollChan(Pb, GrpcMonoObserver(it))
        }.map {
            PollChanReadDto.fromPb(it)
        }
    }

}