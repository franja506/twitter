package com.twitter.shared.config

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.micrometer.core.instrument.kotlin.asContextElement
import io.micrometer.observation.ObservationRegistry
import io.micrometer.tracing.Tracer
import kotlinx.coroutines.withContext
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.server.CoWebFilter
import org.springframework.web.server.CoWebFilterChain
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import java.time.Duration

@Configuration
@EnableCaching
class SeedConfiguration(
    private val observationRegistry: ObservationRegistry,
    private val tracer: Tracer,
    private val webClientBuilder: WebClient.Builder
) {

    @Bean
    fun postWebClient() = webClientBuilder.baseUrl("https://jsonplaceholder.typicode.com").build()

    private fun objectMapper() = ObjectMapper().apply {
        registerKotlinModule()
        registerModule(JavaTimeModule())
        disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        activateDefaultTyping(
            polymorphicTypeValidator,
            ObjectMapper.DefaultTyping.NON_FINAL,
            JsonTypeInfo.As.PROPERTY
        )
    }

    @Bean
    fun cacheConfiguration(): RedisCacheConfiguration {
        return RedisCacheConfiguration
            .defaultCacheConfig()
            .prefixCacheNameWith("seed-cache::")
            .entryTtl(Duration.ofMinutes(10))
            .disableCachingNullValues()
            .serializeValuesWith(
                SerializationPair.fromSerializer(
                    GenericJackson2JsonRedisSerializer(objectMapper())
                )
            )
    }

    @Bean
    fun coroutineWebFilter(): WebFilter {
        return object : CoWebFilter() {
            override suspend fun filter(exchange: ServerWebExchange, chain: CoWebFilterChain) =
                withContext(observationRegistry.asContextElement()) {
                    chain.filter(exchange)
                }
        }
    }
}