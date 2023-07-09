package com.pollvite.pollservice.services

import com.pollvite.grpc.poll.*
import com.pollvite.pollservice.services.businessrules.PollChanBrService
import com.pollvite.pollservice.mappers.PollChanMapper
import com.pollvite.pollservice.models.PollChan
import com.pollvite.pollservice.repositories.PollChanRepository
import com.pollvite.shared.errors.AppException
import com.pollvite.shared.errors.ErrorStatus
import com.pollvite.shared.models.embedded.Audit
import com.pollvite.shared.models.embedded.Timestamps
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

interface PollChanService {
    fun createPollChan(pollCreatePb: PollChanCreatePb) : PollChanReadPb
    fun getPollChanPage(filterPb: PollChanPageFilterPb): PollChanPagePb
    fun editPollChan(pollChanEditPb: PollChanEditPb) : PollChanReadPb
    fun getPollChanById(accessPb: PollChanAccessPb) : PollChanReadPb
    fun deletePollChan(pollChanAccessPb: PollChanAccessPb): PollChanReadPb
}

@Service
private class PollChanServiceImpl(@Autowired private val pollChanRepository: PollChanRepository,
                                  @Autowired private val pollChanBrs: PollChanBrService): PollChanService {

    override fun getPollChanById(accessPb: PollChanAccessPb) : PollChanReadPb {
        val pollChan = getPollChanByIdRequired(accessPb.id)
        return PollChanMapper.modelToReadPb(pollChan)
    }

    override fun getPollChanPage(filterPb: PollChanPageFilterPb): PollChanPagePb {
        val pageable = PageRequest.of(filterPb.pageRequest.page, filterPb.pageRequest.size)
        val page = pollChanRepository.getPageWithFilter(pageable, filterPb.owner, filterPb.titlePattern)
        val pbPage = page.map { PollChanMapper.modelToReadPb(it) }
        return PollChanPagePb.newBuilder().also {
            it.addAllContent(pbPage.content)
            it.total = pbPage.totalElements
        }.build()
    }

    override fun createPollChan(pollCreatePb: PollChanCreatePb) : PollChanReadPb {
        val pollChan = PollChan(id = null,
            core = PollChanMapper.corePbToCoreModel(pollCreatePb.core),
            timestamps = Timestamps.create(),
            audit = Audit(pollCreatePb.core.owner, pollCreatePb.core.owner)
        )
        val saved = pollChanRepository.save(pollChan)
        return PollChanMapper.modelToReadPb(saved)
    }

    override fun editPollChan(pollChanEditPb: PollChanEditPb) : PollChanReadPb {
        val existingPollChan = getPollChanByIdRequired(pollChanEditPb.id)
        pollChanBrs.validateEditPollChan(pollChanEditPb, existingPollChan).throwIfFail()
        val pollChan = PollChan(
            id = existingPollChan.id,
            core = PollChanMapper.corePbToCoreModel(pollChanEditPb.core),
            timestamps = existingPollChan.timestamps.toUpdated(),
            audit = existingPollChan.audit.copy(updatedBy = pollChanEditPb.core.owner)
        )
        val saved = pollChanRepository.save(pollChan)
        return PollChanMapper.modelToReadPb(saved)
    }

    override fun deletePollChan(pollChanAccessPb: PollChanAccessPb): PollChanReadPb {
        val existingPollChan = getPollChanByIdRequired(pollChanAccessPb.id)
        pollChanBrs.validateDeletePollChan(pollChanAccessPb, existingPollChan).throwIfFail()
        pollChanRepository.deleteById(pollChanAccessPb.id)
        return PollChanMapper.modelToReadPb(existingPollChan)
    }

    private fun getPollChanByIdRequired(id: String): PollChan {
        val pollChan = pollChanRepository.findById(id)
        if (pollChan.isEmpty) {
            throw AppException(ErrorStatus.NOT_FOUND)
        }
        return pollChan.get()
    }

}