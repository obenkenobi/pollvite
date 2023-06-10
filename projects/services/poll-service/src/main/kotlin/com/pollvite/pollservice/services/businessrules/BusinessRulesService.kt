package com.pollvite.pollservice.services.businessrules

import com.pollvite.shared.errors.AppException
import com.pollvite.shared.errors.ErrorStatus
import org.apache.logging.log4j.util.Strings
import org.springframework.stereotype.Service

class BusinessRuleResult private constructor(val appException: AppException? = null) {
    companion object {
        fun pass(): BusinessRuleResult = BusinessRuleResult()
        fun fail(ex: AppException): BusinessRuleResult = BusinessRuleResult(appException = ex)
    }
    val isPass get() = appException == null
    val isFail get() = appException != null
    fun <V> ifPassOrFail(passBlock: () -> V, failBlock: (AppException) -> V) : V {
        return if (isPass) passBlock() else failBlock(appException!!)
    }
}

interface BusinessRulesService {
    fun processBrResults(violations: List<String>): BusinessRuleResult
}

@Service
class BusinessRulesServiceImpl : BusinessRulesService {
    override fun processBrResults(violations: List<String>): BusinessRuleResult {
        return if (violations.isNotEmpty())
            BusinessRuleResult.pass()
        else {
            val ex = AppException(ErrorStatus.BR_VIOLATION, Strings.join(violations, ';'))
            BusinessRuleResult.fail(ex)
        }
    }

}