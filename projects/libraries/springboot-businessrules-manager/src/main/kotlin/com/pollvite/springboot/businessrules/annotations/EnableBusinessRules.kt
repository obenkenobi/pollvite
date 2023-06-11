package com.pollvite.springboot.businessrules.annotations

import com.pollvite.springboot.businessrules.services.BusinessRulesServiceImpl
import org.springframework.context.annotation.Import
import java.lang.annotation.Inherited

@Inherited
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@Import(BusinessRulesServiceImpl::class)
annotation class EnableBusinessRules