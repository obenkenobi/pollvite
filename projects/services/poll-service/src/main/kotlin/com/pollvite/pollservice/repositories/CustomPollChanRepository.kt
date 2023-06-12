package com.pollvite.pollservice.repositories

import com.pollvite.pollservice.models.PollChan
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CustomPollChanRepository {
    fun getPageWithFilter(pageable: Pageable, owner: String?, namePattern: String?): Page<PollChan>
}