package com.pollvite.springboot3.resolver.grpc.client.configuration

import org.springframework.boot.autoconfigure.ImportAutoConfiguration

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
abstract class BaseGrpcClientConfig