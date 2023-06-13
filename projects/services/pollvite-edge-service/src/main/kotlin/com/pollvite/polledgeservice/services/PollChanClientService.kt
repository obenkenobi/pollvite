package com.pollvite.polledgeservice.services

import com.pollvite.grpc.poll.PollChanAccessPb
import com.pollvite.grpc.poll.PollChanServiceGrpc.PollChanServiceBlockingStub
import com.pollvite.polledgeservice.dtos.PollChanCreateDto
import com.pollvite.polledgeservice.dtos.PollChanEditDto
import com.pollvite.polledgeservice.dtos.PollChanReadDto
import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

interface PollChanClientService {
    fun getPollChannelById(id: String): PollChanReadDto
    fun createPollChannel(dtoSrc: PollChanCreateDto): PollChanReadDto
    fun editPollChannel(dtoSrc: PollChanEditDto): PollChanReadDto
    fun deletePollChannel(id: String): PollChanReadDto
}

@Service
class PollChanClientServiceImpl(
    @Autowired @GrpcClient("pollService") private val pollChanServiceStub: PollChanServiceBlockingStub,
    @Autowired private val securityService: SecurityService)
    : PollChanClientService {

    override fun getPollChannelById(id: String): PollChanReadDto {
        val userPrincipal = securityService.userPrincipal!!
        val pollChanAccessPb = PollChanAccessPb.newBuilder().also {
            it.id = id
            it.userId = userPrincipal.name
        }.build()
        val pollChanReadPb = pollChanServiceStub.getPollChanById(pollChanAccessPb)
        return PollChanReadDto.fromPb(pollChanReadPb)
    }

    override fun createPollChannel(originalDto: PollChanCreateDto): PollChanReadDto {
        val userPrincipal = securityService.userPrincipal!!
        val dto = originalDto.copy(
            core = originalDto.core?.copy(
                owner = userPrincipal.name))
        val pollChanReadPb = pollChanServiceStub.createPollChan(dto.toPb())
        return PollChanReadDto.fromPb(pollChanReadPb)
    }

    override fun editPollChannel(originalDto: PollChanEditDto): PollChanReadDto {
        val userPrincipal = securityService.userPrincipal!!
        val dto = originalDto.copy(
            core = originalDto.core?.copy(
                owner = userPrincipal.name))
        val pollChanReadPb = pollChanServiceStub.editPollChan(dto.toPb())
        return PollChanReadDto.fromPb(pollChanReadPb)
    }

    override fun deletePollChannel(id: String): PollChanReadDto {
        val userPrincipal = securityService.userPrincipal!!
        val pollChanAccessPb = PollChanAccessPb.newBuilder().also {
            it.id = id
            it.userId = userPrincipal.name
        }.build()
        val pollChanReadPb = pollChanServiceStub.deletePollChan(pollChanAccessPb)
        return PollChanReadDto.fromPb(pollChanReadPb)
    }

}