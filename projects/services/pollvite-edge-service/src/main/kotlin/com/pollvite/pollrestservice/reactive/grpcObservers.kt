package com.pollvite.pollrestservice.reactive

import io.grpc.stub.StreamObserver
import reactor.core.publisher.FluxSink
import reactor.core.publisher.MonoSink

class GrpcMonoObserver<V>(private val s: MonoSink<V>): StreamObserver<V> {
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

class GrpcFluxObserver<V>(private val s: FluxSink<V>): StreamObserver<V> {
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