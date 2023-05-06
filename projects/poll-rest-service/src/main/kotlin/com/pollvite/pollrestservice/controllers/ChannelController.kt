package com.pollvite.pollrestservice.controllers

import com.pollvite.pollrestservice.dtos.IdDto
import com.pollvite.pollrestservice.dtos.PollChanReadDto
import com.pollvite.pollrestservice.services.PollChanRpcService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/channel")
class ChannelController(@Autowired val pollChanRpcService: PollChanRpcService) {

    @GetMapping("/{id}")
    fun helloWorld(@PathVariable id: String): Mono<PollChanReadDto>? {
        return pollChanRpcService.getPollChannelById(id)
    }

    @PostMapping("/idk")
    fun idk(@RequestBody id: IdDto): Mono<IdDto> {
        return Mono.just(IdDto.fromPb(id.toPb()));
    }
}