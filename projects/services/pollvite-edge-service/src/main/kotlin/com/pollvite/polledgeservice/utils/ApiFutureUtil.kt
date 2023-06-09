package com.pollvite.polledgeservice.utils
import com.google.api.core.ApiFuture
import com.google.api.core.ApiFutureCallback
import com.google.api.core.ApiFutures
import reactor.core.publisher.Mono
import reactor.core.publisher.MonoSink

fun <T> ApiFuture<T>.toMono() = Mono.create { sink: MonoSink<T> ->
    val callback: ApiFutureCallback<T> = object : ApiFutureCallback<T> {
        override fun onFailure(t: Throwable) {
            sink.error(t)
        }

        override fun onSuccess(result: T) {
            sink.success(result)
        }
    }
    ApiFutures.addCallback(this, callback) { obj: Runnable -> obj.run() }
}