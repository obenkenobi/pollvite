package com.pollvite.pollrestservice.configuration

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import com.google.protobuf.Message
import com.google.protobuf.Struct
import com.google.protobuf.util.JsonFormat
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer
import java.io.IOException


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
        configurer.defaultCodecs().jackson2JsonDecoder(
            Jackson2JsonDecoder(Jackson2ObjectMapperBuilder.json().deserializerByType(
                Message::class.java, object : JsonDeserializer<Message?>() {
                    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): Message? {
                        val jsonStr = p?.readValueAs(String::class.java)
                        val structBuilder = Struct.newBuilder()
                        JsonFormat.parser().ignoringUnknownFields().merge(jsonStr, structBuilder)
                        return structBuilder.build()
                    }

                }
            ).build())
        )
    }
}