package com.pollvite.userservice.repositories

import com.pollvite.userservice.models.UserProfile
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.data.mongodb.repository.MongoRepository

interface UserProfileRepositoryCustom {
    fun getUpdatedUserProfilesBatchIndex(batchIndexRemainder: Long, batchIndexModulus: Long): List<UserProfile>
}

class UserProfileRepositoryCustomImpl(@Autowired private val mongoTemplate: MongoTemplate)
    : UserProfileRepositoryCustom {

    override fun getUpdatedUserProfilesBatchIndex(
        batchIndexRemainder: Long,
        batchIndexModulus: Long
    ): List<UserProfile> {
        val fbNotSyncedCriteria = Criteria.where("fbSynced").isEqualTo(false)

        val batchIndexMatchCriteria = Criteria
            .expr(ArithmeticOperators.Mod.valueOf("batchIndex").mod(batchIndexModulus))
            .isEqualTo(batchIndexRemainder)

        val criteria = Criteria().andOperator(fbNotSyncedCriteria, batchIndexMatchCriteria)
        val query = Query(criteria)
        return mongoTemplate.find(query, UserProfile::class.java)
    }

}

interface UserProfileRepository: MongoRepository<UserProfile, String>, UserProfileRepositoryCustom {
}