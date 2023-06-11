package com.pollvite.springboot.businessrules

import com.pollvite.shared.errors.AppException

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