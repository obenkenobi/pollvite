package com.pollvite.pollservice.repositories

import com.pollvite.pollservice.models.PollChan
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import reactor.core.publisher.Mono

interface PollChanRepositoryCustom {
    fun getPageWithFilter(pageable: Pageable, owner: String?, titlePattern: String?): Mono<Page<PollChan>>
}