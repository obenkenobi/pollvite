package com.pollvite.pollrestservice.reactive

import io.grpc.stub.StreamObserver
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink
import reactor.core.publisher.Mono
import reactor.core.publisher.MonoSink

private class GrpcMonoSinkObserver<V>(private val s: MonoSink<V>): StreamObserver<V> {
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

private class GrpcFluxSinkObserver<V>(private val s: FluxSink<V>): StreamObserver<V> {
    override fun onNext(value: V?) {
        if (value != null) {
            s.next(value)
        }
    }

    override fun onError(t: Throwable?) {
        if (t != null) {
            s.error(t)
        }
    }

    override fun onCompleted() {
        s.complete()
    }
}

object ReactiveGrpc {
    fun <T, R> callMono(call: (T, StreamObserver<R>) -> Unit, input: T) : Mono<R> {
        return Mono.create {
            call(input, GrpcMonoSinkObserver(it))
        }
    }

    fun <T, R> callFlux(call: (T, StreamObserver<R>) -> Unit, input: T) : Flux<R> {
        return Flux.create {
            call(input, GrpcFluxSinkObserver(it))
        }
    }
}