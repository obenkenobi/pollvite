package com.pollvite.pollrestservice.controllers

import com.pollvite.grpc.poll.PollChannelReadDto
import com.pollvite.pollrestservice.services.PollClientService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController("/channel")
class ChannelController(@Autowired val pollClientService: PollClientService) {
    @GetMapping("/{id}")
    fun helloWorld(@PathVariable("id")id: String): Mono<PollChannelReadDto>? {
        val res = pollClientService.getPollChannelById(id)
        return Mono.fromCallable { res }
    }
}