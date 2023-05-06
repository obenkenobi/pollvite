package com.pollvite.pollrestservice.configuration

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.TreeNode
import com.fasterxml.jackson.databind.*
import com.google.common.reflect.ClassPath
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

        val loader = Thread.currentThread().contextClassLoader
        val deserializers: MutableMap<Class<*>, JsonDeserializer<*>> = HashMap()
        try {
            for (info in ClassPath.from(loader).getTopLevelClasses()) {
                if (info.name.startsWith("com.pollvite.grpc")) {
                    val clazz: Class<*> = info.load()
                    if (!Message::class.java.isAssignableFrom(clazz)) {
                        continue
                    }
                    val proto = clazz as Class<Message>
                    val deserializer: CustomJsonDeserializer = object : CustomJsonDeserializer() {
                        override val deserializeClass: Class<Message>
                            get() = proto
                    }
                    deserializers[proto] = deserializer
                }
            }
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        configurer.defaultCodecs().jackson2JsonDecoder(
            Jackson2JsonDecoder(
                Jackson2ObjectMapperBuilder.json().deserializersByType(deserializers).build()
            )
        )

    }

    private abstract class CustomJsonDeserializer : JsonDeserializer<Message?>() {
        abstract val deserializeClass: Class<out Message?>

        @Throws(IOException::class)
        override fun deserialize(jp: JsonParser, ctxt: DeserializationContext?): Message {
            var builder: Message.Builder? = null
            builder = try {
                deserializeClass
                    .getDeclaredMethod("newBuilder")
                    .invoke(null) as Message.Builder
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
            JsonFormat.parser().merge(jp.codec.readTree<TreeNode>(jp).toString(), builder)
            return builder!!.build()
        }
    }
}