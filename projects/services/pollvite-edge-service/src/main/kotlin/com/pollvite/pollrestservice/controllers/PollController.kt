package com.pollvite.pollrestservice.controllers

import com.pollvite.pollrestservice.dtos.PollChanCreateDto
import com.pollvite.pollrestservice.dtos.PollChanEditDto
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
    fun createPollChannel(@RequestBody @Valid dtoSrc: Mono<PollChanCreateDto>): Mono<PollChanReadDto> {
        return pollChanRpcService.createPollChannel(dtoSrc)
    }

    @PutMapping("/channel")
    fun editPollChannel(@RequestBody @Valid dtoSrc: Mono<PollChanEditDto>): Mono<PollChanReadDto> {
        return pollChanRpcService.editPollChannel(dtoSrc)
    }

    @DeleteMapping("/channel/{id}")
    fun deletePollChannel(@PathVariable id: String): Mono<PollChanReadDto> {
        return pollChanRpcService.deletePollChannel(id)
    }
}