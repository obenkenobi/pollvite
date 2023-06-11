package com.pollvite.pollservice.services.businessrules

import com.pollvite.grpc.poll.PollChanAccessPb
import com.pollvite.grpc.poll.PollChanEditPb
import com.pollvite.pollservice.models.PollChan
import com.pollvite.springboot.businessrules.BusinessRuleResult
import com.pollvite.springboot.businessrules.services.BusinessRulesService
import org.springframework.stereotype.Component

interface PollChanBrService {
    fun validateDeletePollChan(accessPb: PollChanAccessPb, existing: PollChan): BusinessRuleResult
    fun validateEditPollChan(editPb: PollChanEditPb, existing: PollChan): BusinessRuleResult
}

@Component
class PollChanBRsImpl(val businessRulesService: BusinessRulesService): PollChanBrService {
    override fun validateDeletePollChan(accessPb: PollChanAccessPb, existing: PollChan): BusinessRuleResult {
        val violations = ArrayList<String>()
        if (accessPb.userId != existing.core.owner) {
            violations.add("Not a poll owner")
        }
        return businessRulesService.processBrResults(violations = violations)
    }

    override fun validateEditPollChan(editPb: PollChanEditPb, existing: PollChan): BusinessRuleResult {
        val violations = ArrayList<String>()
        if (editPb.core.owner != existing.core.owner) {
            violations.add("Not a poll owner")
        }
        return businessRulesService.processBrResults(violations = violations)
    }
}