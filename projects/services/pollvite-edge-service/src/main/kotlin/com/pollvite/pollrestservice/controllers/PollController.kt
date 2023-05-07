package com.pollvite.pollrestservice.controllers

import com.pollvite.pollrestservice.dtos.PollChanCreateDto
import com.pollvite.pollrestservice.dtos.PollChanReadDto
import com.pollvite.pollrestservice.services.PollChanRpcService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import javax.validation.Valid

@RestController
@RequestMapping("api/poll")
class PollController(@Autowired val pollChanRpcService: PollChanRpcService) {

    @GetMapping("/channel/{id}")
    fun getPollCHanById(@PathVariable id: String): Mono<PollChanReadDto>? {
        return pollChanRpcService.getPollChannelById(id)
    }

    @PostMapping("/channel")
    fun idk(@RequestBody @Valid dtoSrc: Mono<PollChanCreateDto>): Mono<PollChanReadDto> {
        return pollChanRpcService.createPollChannel(dtoSrc)
    }
}