package com.pollvite.polledgeservice.controllers.rest

import com.pollvite.polledgeservice.dtos.PollChanCreateDto
import com.pollvite.polledgeservice.dtos.PollChanEditDto
import com.pollvite.polledgeservice.dtos.PollChanReadDto
import com.pollvite.polledgeservice.services.PollChanClientService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/poll")
class PollController(@Autowired private val pollChanClientService: PollChanClientService) {

    @GetMapping("/channel/{id}")
    fun getPollCHanById(@PathVariable id: String): PollChanReadDto {
        return pollChanClientService.getPollChannelById(id)
    }

    @PostMapping("/channel")
    fun createPollChannel(@RequestBody @Valid dto: PollChanCreateDto): PollChanReadDto {
        return pollChanClientService.createPollChannel(dto)
    }

    @PutMapping("/channel")
    fun editPollChannel(@RequestBody @Valid dto: PollChanEditDto): PollChanReadDto {
        return pollChanClientService.editPollChannel(dto)
    }

    @DeleteMapping("/channel/{id}")
    fun deletePollChannel(@PathVariable id: String): PollChanReadDto {
        return pollChanClientService.deletePollChannel(id)
    }
}