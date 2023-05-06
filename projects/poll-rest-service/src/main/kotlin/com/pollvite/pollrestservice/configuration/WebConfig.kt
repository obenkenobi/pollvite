package com.pollvite.pollrestservice.configuration

import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import kotlin.Throws
import java.io.IOException
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.protobuf.Message
import com.google.protobuf.util.JsonFormat
import org.springframework.context.annotation.Configuration

@Configuration
@EnableWebFlux
class WebConfig : WebFluxConfigurer {
    override fun configureHttpMessageCodecs(configurer: ServerCodecConfigurer) {
        configurer.defaultCodecs().jackson2JsonEncoder(
            Jackson2JsonEncoder(Jackson2ObjectMapperBuilder.json().serializerByType(
                Message::class.java, object : JsonSerializer<Message?>() {
                    @Throws(IOException::class)
                    override fun serialize(value: Message?, gen: JsonGenerator, serializers: SerializerProvider) {
                        val str = JsonFormat.printer().omittingInsignificantWhitespace().print(value)
                        gen.writeRawValue(str)
                    }
                }
            ).build())
        )
    }
}