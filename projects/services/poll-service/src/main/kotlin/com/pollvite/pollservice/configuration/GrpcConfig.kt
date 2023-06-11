package com.pollvite.pollservice.configuration

import com.pollvite.springboot.resolver.grpc.client.annotations.EnableGrpcClient
import com.pollvite.springboot.resolver.grpc.server.annotations.EnableGrpcServer
import org.springframework.context.annotation.Configuration

@Configuration
@EnableGrpcClient
@EnableGrpcServer
class GrpcConfig
