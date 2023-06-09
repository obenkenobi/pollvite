package com.pollvite.springboot.resolver.grpc.client.annotations

import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import java.lang.annotation.Inherited

/**
 * Activates Grpc Client dependencies for Spring Boot 3.
 * This may not be needed for later versions of net.devh:grpc-client-spring-boot-starter.
 * */
@Inherited
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@ImportAutoConfiguration(
    net.devh.boot.grpc.client.autoconfigure.GrpcClientAutoConfiguration::class,
    net.devh.boot.grpc.client.autoconfigure.GrpcClientMetricAutoConfiguration::class,
    net.devh.boot.grpc.client.autoconfigure.GrpcClientHealthAutoConfiguration::class,
    net.devh.boot.grpc.client.autoconfigure.GrpcClientSecurityAutoConfiguration::class,
    net.devh.boot.grpc.client.autoconfigure.GrpcClientTraceAutoConfiguration::class,
    net.devh.boot.grpc.client.autoconfigure.GrpcDiscoveryClientAutoConfiguration::class,
    net.devh.boot.grpc.common.autoconfigure.GrpcCommonCodecAutoConfiguration::class,
    net.devh.boot.grpc.common.autoconfigure.GrpcCommonTraceAutoConfiguration::class,
)
annotation class EnableGrpcClient