package com.pollvite.springboot.businessrules

import com.pollvite.shared.errors.AppException

/**
 * Returned result from processing your business rules.
 * @property appException The exception created if your business rules failed
 * */
class BusinessRuleResult private constructor(val appException: AppException? = null) {
    companion object {
        /**
         * Creates a passing [BusinessRuleResult]. Use this method if your business rules passed.
         * @return A passing [BusinessRuleResult].
         * */
        fun pass(): BusinessRuleResult = BusinessRuleResult()
        /**
         * Creates a failing [BusinessRuleResult]. Use this method if your business rules failed.
         * @param ex An [AppException] instance describing why your business rules failed.
         * @return A failing [BusinessRuleResult].
         * */
        fun fail(ex: AppException): BusinessRuleResult = BusinessRuleResult(appException = ex)
    }
    /**
     * If your business rules result is passing.
     * */
    val isPass get() = appException == null
    /**
     * If your business rules result is failing.
     * */
    val isFail get() = appException != null
    /**
     * Wrapper method to handle if your business rules have passed or failed.
     * @param V The type to be returned when handling if your business rules passed or failed.
     * @param passBlock If your business rules passed, this function / functional interface will run.
     * It's return value will be the same as the return value for the whole [ifPassOrFail] method.
     * @param failBlock If your business rules failed, this function / functional interface will run.
     * This function will provide an [AppException] stored from a failing business result.
     * It's return value will be the same as the return value for the whole [ifPassOrFail] method.
     * @return A value that is returned from either the [passBlock] or the [failBlock].
     * */
    fun <V> ifPassOrFail(passBlock: () -> V, failBlock: (AppException) -> V) : V {
        return if (isPass) passBlock() else failBlock(appException!!)
    }
}