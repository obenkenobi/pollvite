package com.pollvite.pollrestservice.services

import com.pollvite.grpc.poll.PollChanCreateDto
import com.pollvite.grpc.poll.PollChanReadDto
import com.pollvite.grpc.poll.PollChanServiceGrpc.PollChanServiceBlockingStub
import com.pollvite.grpc.shared.IdDto
import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class PollClientService(
    @Autowired @GrpcClient("pollService") private val pollService: PollChanServiceBlockingStub? = null) {

    fun getPollChannelById(id: String): PollChanReadDto? {
        return pollService?.getPollChanById(IdDto.newBuilder().setValue(id).build())
    }

    fun createPollChannel(dto: PollChanCreateDto): PollChanReadDto? {
        return pollService?.createPollChan(dto)
    }

}