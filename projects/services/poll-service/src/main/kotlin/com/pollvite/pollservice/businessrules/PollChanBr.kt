package com.pollvite.pollservice.businessrules

import com.pollvite.grpc.poll.PollChanAccessPb
import com.pollvite.pollservice.models.PollChan
import com.pollvite.shared.errors.AppException
import com.pollvite.shared.errors.ErrorStatus
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class PollChanBr {
    fun validatePollChanDelete(accessPb: PollChanAccessPb, existing: PollChan): List<String> {
        val violations = ArrayList<String>()
        if (accessPb.userId != existing.core.owner) {
            violations.add("Not a poll owner")
        }
        return violations
    }
}