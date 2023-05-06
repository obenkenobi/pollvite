package com.pollvite.pollrestservice.controllers

import com.pollvite.grpc.poll.PollChanReadDto
import com.pollvite.pollrestservice.services.PollClientService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/channel", produces = [MediaType.APPLICATION_JSON_VALUE])
class ChannelController(@Autowired val pollClientService: PollClientService) {

    @GetMapping("/{id}")
    fun helloWorld(@PathVariable id: String): Mono<PollChanReadDto>? {
        return Mono.fromCallable {
            val res = pollClientService.getPollChannelById(id)
            res
        }
    }

    @GetMapping("/also/{id}")
    fun idk(@PathVariable("id")id: String): String? {
        return id
    }
}