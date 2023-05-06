package com.pollvite.pollrestservice.services

import com.pollvite.grpc.poll.PollChanCreatePb
import com.pollvite.grpc.poll.PollChanServiceGrpc.PollChanServiceStub
import com.pollvite.grpc.shared.IdPb
import com.pollvite.pollrestservice.dtos.PollChanReadDto
import com.pollvite.pollrestservice.grpcutils.GrpcMonoObserver
import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

interface PollChanRpcService {
    fun getPollChannelById(id: String): Mono<PollChanReadDto>
    fun createPollChannel(Pb: PollChanCreatePb): Mono<PollChanReadDto>
}

@Service
class PollChanRpcServiceImpl(
    @Autowired @GrpcClient("pollService") private val pollChanServiceClient: PollChanServiceStub? = null)
    : PollChanRpcService {

    override fun getPollChannelById(id: String): Mono<PollChanReadDto> {
        return Mono.create { it ->
            val idPb = IdPb.newBuilder().also { it.value = id }.build()
            pollChanServiceClient?.getPollChanById(idPb, GrpcMonoObserver(it))
        }.map {
            PollChanReadDto.fromPb(it)
        }
    }

    override fun createPollChannel(Pb: PollChanCreatePb): Mono<PollChanReadDto> {
        return Mono.create {
            pollChanServiceClient?.createPollChan(Pb, GrpcMonoObserver(it))
        }.map {
            PollChanReadDto.fromPb(it)
        }
    }

}