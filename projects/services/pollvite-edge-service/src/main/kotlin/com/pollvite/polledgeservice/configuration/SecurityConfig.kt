package com.pollvite.polledgeservice.configuration

import com.pollvite.polledgeservice.security.AuthFilter
import com.pollvite.polledgeservice.security.CsrfLoadFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.security.web.csrf.CsrfFilter
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler


@Configuration
@EnableWebSecurity
class SecurityConfig(@Autowired private val securityFilter: AuthFilter,
                     @Autowired private val  csrfLoadFilter: CsrfLoadFilter) {
    @Bean
    @kotlin.jvm.Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf {
                it.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                    .csrfTokenRequestHandler(CsrfTokenRequestAttributeHandler())
            }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .authorizeHttpRequests { authorize -> authorize
                .requestMatchers("/api/auth/login", "/api/auth/csrf").permitAll()
                .requestMatchers("/api/conf/**").permitAll()
                .requestMatchers("/api/**").authenticated()
                .requestMatchers(HttpMethod.GET).permitAll()
                .anyRequest().authenticated()
            }
            .addFilterAfter(csrfLoadFilter, CsrfFilter::class.java)
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter::class.java)
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .build()
    }
}