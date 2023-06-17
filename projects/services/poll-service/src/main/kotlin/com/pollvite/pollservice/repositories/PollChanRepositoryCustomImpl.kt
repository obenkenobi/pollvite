package com.pollvite.pollservice.repositories

import com.pollvite.pollservice.models.PollChan
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

class PollChanRepositoryCustomImpl(@Autowired private val mongoTemplate: MongoTemplate) : PollChanRepositoryCustom {

    override fun getPageWithFilter(pageable: Pageable, owner: String?, titlePattern: String?): Page<PollChan> {
        val filterCriteriaList = listOfNotNull(
            if (owner.isNullOrBlank()) null else Criteria.where("core.owner").`is`(owner),
            if (titlePattern.isNullOrBlank()) null else Criteria.where("core.titleId").regex(titlePattern),
        )
        val criteria = Criteria().andOperator(filterCriteriaList)
        val query = Query(criteria).with(pageable)
        val findResult = mongoTemplate.find(query, PollChan::class.java)
        val countResult = mongoTemplate.count(query, PollChan::class.java)
        return PageImpl(findResult, pageable, countResult)
    }

    override fun getByTitleId(titleId: String): PollChan? {
        val criteria = Criteria.where("core.titleId").`is`(titleId)
        val query = Query(criteria)
        return mongoTemplate.findOne(query, PollChan::class.java)
    }
}