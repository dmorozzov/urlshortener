package com.codefactory.url_shortener.domain.url.repository

import com.codefactory.url_shortener.domain.url.exception.InternalShortenerException
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Repository


const val URL_ID_SEQUENCE = "URL_ID_SEQUENCE"

@Repository
class SequenceRepository(private val redisTemplate: RedisTemplate<String, String>) {
    fun incrementAndGet(): Long {
        val valueOps: ValueOperations<String, String> = redisTemplate.opsForValue()
        return valueOps.increment(URL_ID_SEQUENCE, 1) ?: throw InternalShortenerException("Couldn't acquire next id")
    }
}
