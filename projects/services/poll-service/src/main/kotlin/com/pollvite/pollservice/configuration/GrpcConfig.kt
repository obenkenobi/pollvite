package com.pollvite.pollservice.configuration

import com.pollvite.springboot3.resolver.grpc.client.configuration.BaseGrpcClientConfig
import com.pollvite.springboot3.resolver.grpc.server.configuration.BaseGrpcServerConfig
import org.springframework.context.annotation.Configuration

@Configuration
class GrpcServerConfig : BaseGrpcServerConfig()

@Configuration
class GrpcClientConfig : BaseGrpcClientConfig()
