package com.pollvite.identityservice.repositories

import com.pollvite.identityservice.models.UserProfile
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.data.mongodb.repository.MongoRepository

interface UserProfileRepositoryCustom {
    fun getActionableBatch(batchNumber: Long, batchCount: Long): List<UserProfile>
}

class UserProfileRepositoryCustomImpl(@Autowired private val mongoTemplate: MongoTemplate)
    : UserProfileRepositoryCustom {

    override fun getActionableBatch(batchNumber: Long, batchCount: Long): List<UserProfile> {
        val actionCt = Criteria.where("action").ne(UserProfile.Action.NONE)

        val idxModBatchCountOp = ArithmeticOperators.Mod.valueOf("batchIndex").mod(batchCount)
        val batchNumberMatchCt = Criteria.expr(idxModBatchCountOp).isEqualTo(batchNumber)

        val criteria = Criteria().andOperator(actionCt, batchNumberMatchCt)
        val query = Query(criteria)
        return mongoTemplate.find(query, UserProfile::class.java)
    }

}

interface UserProfileRepository: MongoRepository<UserProfile, String>, UserProfileRepositoryCustom