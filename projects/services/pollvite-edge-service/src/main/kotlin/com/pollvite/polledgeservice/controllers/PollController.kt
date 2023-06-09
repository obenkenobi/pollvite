package com.pollvite.polledgeservice.controllers

import com.pollvite.polledgeservice.dtos.PollChanCreateDto
import com.pollvite.polledgeservice.dtos.PollChanEditDto
import com.pollvite.polledgeservice.dtos.PollChanReadDto
import com.pollvite.polledgeservice.services.PollChanClientService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import jakarta.validation.Valid

@RestController
@RequestMapping("api/poll")
class PollController(@Autowired private val pollChanClientService: PollChanClientService) {

    @GetMapping("/channel/{id}")
    fun getPollCHanById(@PathVariable id: String): Mono<PollChanReadDto>? {
        return pollChanClientService.getPollChannelById(id)
    }

    @PostMapping("/channel")
    fun createPollChannel(@RequestBody @Valid dtoSrc: Mono<PollChanCreateDto>): Mono<PollChanReadDto> {
        return pollChanClientService.createPollChannel(dtoSrc)
    }

    @PutMapping("/channel")
    fun editPollChannel(@RequestBody @Valid dtoSrc: Mono<PollChanEditDto>): Mono<PollChanReadDto> {
        return pollChanClientService.editPollChannel(dtoSrc)
    }

    @DeleteMapping("/channel/{id}")
    fun deletePollChannel(@PathVariable id: String): Mono<PollChanReadDto> {
        return pollChanClientService.deletePollChannel(id)
    }
}