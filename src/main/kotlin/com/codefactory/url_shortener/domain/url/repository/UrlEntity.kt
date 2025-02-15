package com.codefactory.url_shortener.domain.url.repository

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash
data class UrlEntity(
    @field:Id
    var id: Long,
    var shortUrl: String,
    var originalUrl: String
)
