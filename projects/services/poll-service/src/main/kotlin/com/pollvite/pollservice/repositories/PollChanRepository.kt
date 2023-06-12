package com.pollvite.pollservice.repositories

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import com.pollvite.pollservice.models.PollChan

interface PollChanRepository : ReactiveMongoRepository<PollChan, String>, PollChanRepositoryCustom