package com.pollvite.pollservice.services

import com.pollvite.pollservice.businessrules.PollChanBRs
import com.pollvite.shared.errors.AppException
import com.pollvite.shared.errors.ErrorStatus
import org.apache.logging.log4j.util.Strings
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BusinessRulesService(@Autowired private val pollChanBrs: PollChanBRs) {
    fun processBrResults(violations: List<String>): AppException? {
        if (violations.isEmpty())  {
            return null
        }
        return AppException(ErrorStatus.BR_VIOLATION, Strings.join(violations, ';'))
    }

}