package com.pollvite.springboot.businessrules.services

import com.pollvite.shared.errors.AppException
import com.pollvite.shared.errors.ErrorStatus
import com.pollvite.springboot.businessrules.BusinessRuleResult
import org.apache.logging.log4j.util.Strings
import org.springframework.stereotype.Service

interface BusinessRulesService {
    fun processBrResults(violations: List<String>): BusinessRuleResult
}

@Service
class BusinessRulesServiceImpl : BusinessRulesService {
    override fun processBrResults(violations: List<String>): BusinessRuleResult {
        return if (violations.isEmpty())
            BusinessRuleResult.pass()
        else {
            val ex = AppException(ErrorStatus.BR_VIOLATION, Strings.join(violations, ';'))
            BusinessRuleResult.fail(ex)
        }
    }

}