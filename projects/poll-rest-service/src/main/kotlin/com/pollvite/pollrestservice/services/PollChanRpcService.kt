package com.pollvite.pollrestservice.services

import com.pollvite.grpc.poll.PollChanCreateDto
import com.pollvite.grpc.poll.PollChanReadDto
import com.pollvite.grpc.poll.PollChanServiceGrpc.PollChanServiceStub
import com.pollvite.grpc.shared.IdDto
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.publisher.MonoSink

class GrpcMonoObserver<V>(private val s: MonoSink<V> ): StreamObserver<V> {
    override fun onNext(value: V?) {
        s.success(value)
    }

    override fun onError(t: Throwable?) {
        if (t != null) {
            s.error(t)
        }
    }

    override fun onCompleted() {
        s.success()
    }
}


@Service
class PollChanRpcService(
    @Autowired @GrpcClient("pollService") private val pollChanService: PollChanServiceStub? = null) {

    fun getPollChannelById(id: String): Mono<PollChanReadDto> {
        return Mono.create {
            pollChanService?.getPollChanById(IdDto.newBuilder().setValue(id).build(), GrpcMonoObserver(it))
        }
    }

    fun createPollChannel(dto: PollChanCreateDto): Mono<PollChanReadDto> {
        return Mono.create {
            pollChanService?.createPollChan(dto, GrpcMonoObserver(it))
        }
    }

}