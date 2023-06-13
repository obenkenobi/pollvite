package com.pollvite.pollservice.repositories

import com.pollvite.pollservice.models.PollChan
import org.springframework.data.mongodb.repository.MongoRepository

interface PollChanRepository : MongoRepository<PollChan, String>, PollChanRepositoryCustom