package com.pollvite.springboot.businessrules.annotations

import org.springframework.context.annotation.ComponentScan
import java.lang.annotation.Inherited

/**
 * Enables business rules dependencies to be added as Spring dependencies.
 * */
@Inherited
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@ComponentScan(basePackages = ["com.pollvite.springboot.businessrules"])
annotation class EnableBusinessRules