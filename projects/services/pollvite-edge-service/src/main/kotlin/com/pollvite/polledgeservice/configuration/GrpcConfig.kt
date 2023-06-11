package com.pollvite.polledgeservice.configuration

import com.pollvite.springboot.resolver.grpc.client.annotations.EnableGrpcClient
import org.springframework.context.annotation.Configuration

@Configuration
@EnableGrpcClient
class GrpcConfig