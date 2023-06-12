package com.pollvite.pollservice.repositories

import com.pollvite.pollservice.models.PollChan
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import reactor.core.publisher.Mono

class PollChanRepositoryCustomImpl(@Autowired private val mongoTemplate: ReactiveMongoTemplate)
    : PollChanRepositoryCustom {

    override fun getPageWithFilter(pageable: Pageable, owner: String?, titlePattern: String?): Mono<Page<PollChan>> {
        val filterCriteriaList = listOfNotNull(
            if (owner != null) Criteria.where("core.owner").`is`(owner) else null,
            if (titlePattern != null) Criteria.where("core.title").regex(titlePattern) else null,
        )
        val criteria = Criteria().andOperator(filterCriteriaList)
        val query = Query(criteria).with(pageable)
        val findResult = mongoTemplate.find(query, PollChan::class.java).collectList()
        val countResult = mongoTemplate.count(query, PollChan::class.java)
        val res = Mono.zip(findResult, countResult) { content, total ->
            val pageRes: Page<PollChan> = PageImpl(content, pageable, total)
            pageRes
        }
        return res
    }
}