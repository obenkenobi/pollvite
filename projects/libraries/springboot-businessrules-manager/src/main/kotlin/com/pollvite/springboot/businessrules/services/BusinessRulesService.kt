package com.pollvite.springboot.businessrules.services

import com.pollvite.shared.errors.AppException
import com.pollvite.shared.errors.ErrorStatus
import com.pollvite.springboot.businessrules.BusinessRuleResult
import org.apache.logging.log4j.util.Strings
import org.springframework.stereotype.Service

/**
 * A service that handles business rules.
 * */
interface BusinessRulesService {
    /**
     * Process business rule results, in the form of a list of [violations].
     * If the list of [violations] is empty, a passing [BusinessRuleResult] is returned.
     * Otherwise, if the list of [violations] is not empty, a failing [BusinessRuleResult] is returned.
     * The [AppException] in the failing [BusinessRuleResult]
     * contains a message of violation messages joined by a [separator].
     * @param violations A list of violation messages from validating if your business rules have passed.
     * @param separator A char separator that is used when joining messages in [violations]
     * into a single message [String] to be added as a message in the [AppException]
     * inside the failing [BusinessRuleResult] return value.
     * @return A passing or failing [BusinessRuleResult].
     */
    fun processBrResults(violations: List<String>, separator: Char = ';'): BusinessRuleResult
}

@Service
class BusinessRulesServiceImpl : BusinessRulesService {
    override fun processBrResults(violations: List<String>, separator: Char): BusinessRuleResult {
        return if (violations.isEmpty())
            BusinessRuleResult.pass()
        else {
            val ex = AppException(ErrorStatus.BR_VIOLATION, Strings.join(violations, separator))
            BusinessRuleResult.fail(ex)
        }
    }

}