package com.pollvite.springboot.resolver.grpc.server.annotations

import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import java.lang.annotation.Inherited

/**
 * Activates Grpc Server dependencies for Spring Boot 3.
 * This may not be needed for later versions of net.devh:grpc-server-spring-boot-starter.
 * */
@Inherited
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@ImportAutoConfiguration(
    net.devh.boot.grpc.common.autoconfigure.GrpcCommonCodecAutoConfiguration::class,
    net.devh.boot.grpc.common.autoconfigure.GrpcCommonTraceAutoConfiguration::class,
    net.devh.boot.grpc.server.autoconfigure.GrpcAdviceAutoConfiguration::class,
    net.devh.boot.grpc.server.autoconfigure.GrpcHealthServiceAutoConfiguration::class,
    net.devh.boot.grpc.server.autoconfigure.GrpcMetadataConsulConfiguration::class,
    net.devh.boot.grpc.server.autoconfigure.GrpcMetadataEurekaConfiguration::class,
    net.devh.boot.grpc.server.autoconfigure.GrpcMetadataNacosConfiguration::class,
    net.devh.boot.grpc.server.autoconfigure.GrpcMetadataZookeeperConfiguration::class,
    net.devh.boot.grpc.server.autoconfigure.GrpcReflectionServiceAutoConfiguration::class,
    net.devh.boot.grpc.server.autoconfigure.GrpcServerAutoConfiguration::class,
    net.devh.boot.grpc.server.autoconfigure.GrpcServerFactoryAutoConfiguration::class,
    net.devh.boot.grpc.server.autoconfigure.GrpcServerMetricAutoConfiguration::class,
    net.devh.boot.grpc.server.autoconfigure.GrpcServerSecurityAutoConfiguration::class,
    net.devh.boot.grpc.server.autoconfigure.GrpcServerTraceAutoConfiguration::class,
)
annotation class EnableGrpcServer

