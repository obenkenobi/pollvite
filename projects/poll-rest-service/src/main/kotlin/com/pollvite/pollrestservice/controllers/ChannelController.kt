package com.pollvite.pollrestservice.controllers

import com.pollvite.grpc.poll.PollChanReadPb
import com.pollvite.grpc.shared.IdPb
import com.pollvite.pollrestservice.services.PollChanRpcService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/channel", produces = [MediaType.APPLICATION_JSON_VALUE])
class ChannelController(@Autowired val pollChanRpcService: PollChanRpcService) {

    @GetMapping("/{id}")
    fun helloWorld(@PathVariable id: String): Mono<PollChanReadPb>? {
        return pollChanRpcService.getPollChannelById(id)
    }

    @PostMapping("/idk", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun idk(@RequestBody id: IdPb): Mono<IdPb> {
        return Mono.just(id);
    }
}