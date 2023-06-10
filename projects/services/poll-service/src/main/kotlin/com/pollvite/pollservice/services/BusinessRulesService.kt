package com.pollvite.pollservice.services

import com.pollvite.shared.errors.AppException
import com.pollvite.shared.errors.ErrorStatus
import org.apache.logging.log4j.util.Strings
import org.springframework.stereotype.Service

interface BusinessRulesService {
    fun processBrResults(violations: List<String>): AppException?
}

@Service
class BusinessRulesServiceImpl : BusinessRulesService {
    override fun processBrResults(violations: List<String>): AppException? {
        if (violations.isEmpty())  {
            return null
        }
        return AppException(ErrorStatus.BR_VIOLATION, Strings.join(violations, ';'))
    }

}