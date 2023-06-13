package com.pollvite.pollservice.configuration

import com.pollvite.springboot.resolver.grpc.client.annotations.EnableGrpcClient
import com.pollvite.springboot.resolver.grpc.server.annotations.EnableGrpcServer
import io.grpc.ServerBuilder
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder
import net.devh.boot.grpc.server.serverfactory.GrpcServerConfigurer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


@Configuration
@EnableGrpcClient
@EnableGrpcServer
class GrpcConfig {
    companion object {
        private val log: Logger = LoggerFactory.getLogger(GrpcConfig::class.java)
    }

    @Bean(name = ["grpcServerExecutor"], destroyMethod = "shutdown")
    fun grpcServerExecutor(): ExecutorService? {
        return Executors.newVirtualThreadPerTaskExecutor()
    }

    @Bean
    fun keepAliveServerConfigurer(@Qualifier("grpcServerExecutor") executor: ExecutorService): GrpcServerConfigurer {
        return GrpcServerConfigurer { serverBuilder: ServerBuilder<*>? ->
            if (serverBuilder is NettyServerBuilder) {
                log.info("Configured server executor!!!")
                serverBuilder
                    .executor(executor)
                    .keepAliveTime(30, TimeUnit.SECONDS)
                    .keepAliveTimeout(5, TimeUnit.SECONDS)
                    .permitKeepAliveWithoutCalls(true)
            }
        }
    }
}
