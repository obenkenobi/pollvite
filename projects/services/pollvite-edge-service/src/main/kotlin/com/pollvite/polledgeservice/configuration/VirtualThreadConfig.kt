package com.pollvite.polledgeservice.configuration

//import org.apache.coyote.ProtocolHandler
//import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration
//import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer
//import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
//import org.springframework.core.task.AsyncTaskExecutor
//import org.springframework.core.task.support.TaskExecutorAdapter
//import java.util.concurrent.Executors

// Todo: Enable virtual threads when moving Java 21
@Configuration
class VirtualThreadConfig {
//    @Bean(TaskExecutionAutoConfiguration.APPLICATION_TASK_EXECUTOR_BEAN_NAME)
//    fun asyncTaskExecutor(): AsyncTaskExecutor? {
//        return TaskExecutorAdapter(Executors.newVirtualThreadPerTaskExecutor())
//    }
//
//    @Bean
//    fun protocolHandlerVirtualThreadExecutorCustomizer(): TomcatProtocolHandlerCustomizer<*>? {
//        return TomcatProtocolHandlerCustomizer<ProtocolHandler> { protocolHandler: ProtocolHandler ->
//            protocolHandler.executor = Executors.newVirtualThreadPerTaskExecutor()
//        }
//    }
}