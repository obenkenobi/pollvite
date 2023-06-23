package com.pollvite.userservice.background

import com.pollvite.userservice.models.UserProfile
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.data.mongodb.core.ChangeStreamEvent
import org.springframework.data.mongodb.core.ChangeStreamOptions
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.changeStream
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Component
import reactor.core.scheduler.Schedulers


@Component
class UserUpdateJob(@Autowired val reactiveMongoTemplate: ReactiveMongoTemplate) {
    companion object {
        private val log: Logger = LoggerFactory.getLogger(UserUpdateJob::class.java)
    }

    @EventListener(ContextRefreshedEvent::class)
    fun listenChangeStream() {
        val fbNotSyncedCriteria = Criteria().orOperator(listOf(
            Criteria.where("fbSynced").exists(false),
            Criteria.where("fbSynced").isEqualTo(false)
        ))
        val options: ChangeStreamOptions = ChangeStreamOptions.builder()
            .filter(Aggregation.newAggregation(UserProfile::class.java, Aggregation.match(fbNotSyncedCriteria)))
            .returnFullDocumentOnUpdate().build()

        // return a flux that watches the changestream and returns the full document

        // return a flux that watches the changestream and returns the full document
        reactiveMongoTemplate.changeStream<UserProfile>()
            .listen()
            .subscribeOn(Schedulers.boundedElastic())
            .map { obj: ChangeStreamEvent<UserProfile> ->
                // Todo update fb data
                val userProfile = obj.body
                log.debug("User profile {} changed", userProfile)
                obj
            }.doOnError { throwable ->
                log.error(
                    "Error with the samples changestream event: " + throwable.message,
                    throwable
                )
            }
            .subscribe()
    }
}