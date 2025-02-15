package com.codefactory.url_shortener.domain.url.model

import com.codefactory.url_shortener.domain.url.repository.UrlEntity

data class UrlRecord(
    val id: Long,
    val shortUrl: String,
    val originalUrl: String
) {
    companion object {
        fun fromUrlEntity(urlEntity: UrlEntity): UrlRecord =
            UrlRecord(
                id = urlEntity.id,
                shortUrl = urlEntity.shortUrl,
                originalUrl = urlEntity.originalUrl
            )
    }
}
