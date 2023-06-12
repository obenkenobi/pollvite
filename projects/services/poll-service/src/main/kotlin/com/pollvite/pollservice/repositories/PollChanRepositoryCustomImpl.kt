package com.pollvite.pollservice.repositories

import com.pollvite.pollservice.models.PollChan
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.ReactiveMongoTemplate

class PollChanRepositoryCustomImpl(@Autowired private val mongoTemplate: ReactiveMongoTemplate): PollChanRepositoryCustom {
    override fun getPageWithFilter(pageable: Pageable, owner: String?, namePattern: String?): Page<PollChan> {
        TODO("Not yet implemented")
    }
}