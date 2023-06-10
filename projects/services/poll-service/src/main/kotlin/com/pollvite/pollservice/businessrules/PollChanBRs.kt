package com.pollvite.pollservice.businessrules

import com.pollvite.grpc.poll.PollChanAccessPb
import com.pollvite.pollservice.models.PollChan
import org.springframework.stereotype.Component

interface PollChanBRs {
    fun validatePollChanDelete(accessPb: PollChanAccessPb, existing: PollChan): List<String>
}

@Component
class PollChanBRsImpl: PollChanBRs {
    override fun validatePollChanDelete(accessPb: PollChanAccessPb, existing: PollChan): List<String> {
        val violations = ArrayList<String>()
        if (accessPb.userId != existing.core.owner) {
            violations.add("Not a poll owner")
        }
        return violations
    }
}