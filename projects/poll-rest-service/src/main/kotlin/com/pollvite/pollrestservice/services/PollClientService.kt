package com.pollvite.pollrestservice.services

import com.pollvite.grpc.poll.PollChannelCreateDto
import com.pollvite.grpc.poll.PollChannelReadDto
import com.pollvite.grpc.poll.PollServiceGrpc.PollServiceBlockingStub
import com.pollvite.grpc.shared.IdDto
import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class PollClientService(@Autowired @GrpcClient("pollService") private val pollService: PollServiceBlockingStub? = null) {

    fun getPollChannelById(id: String): PollChannelReadDto? {
        return pollService?.getPollChannelById(IdDto.newBuilder().setId(id).build())
    }

    fun createPollChannel(dto: PollChannelCreateDto): PollChannelReadDto? {
        return pollService?.createPollChannel(dto)
    }

}