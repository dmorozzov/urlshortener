package com.codefactory.url_shortener.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate

@Configuration
class RedisConfig {
    @Bean
    fun redisTemplate(connectionFactory: RedisConnectionFactory?): RedisTemplate<String, String> {
        val template: RedisTemplate<String, String> = RedisTemplate<String, String>()
        template.connectionFactory = connectionFactory
        template.setEnableTransactionSupport(true)
        return template
    }
}
