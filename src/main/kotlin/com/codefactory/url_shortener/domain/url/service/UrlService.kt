package com.codefactory.url_shortener.domain.url.service

import com.codefactory.url_shortener.domain.url.model.UrlRecord
import com.codefactory.url_shortener.domain.url.repository.SequenceRepository
import com.codefactory.url_shortener.domain.url.repository.UrlEntity
import com.codefactory.url_shortener.domain.url.repository.UrlRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UrlService(
    private val urlRepository: UrlRepository,
    private val sequenceRepository: SequenceRepository
) {
    @Transactional
    fun createRecord(originalUrl: String): UrlRecord {
        val urlId = sequenceRepository.incrementAndGet()
        val urlEntity = urlRepository.save(
            UrlEntity(
                id = urlId,
                shortUrl = UrlEncoder.encode(urlId),
                originalUrl = originalUrl)
        )
        return UrlRecord.fromUrlEntity(urlEntity)
    }
}
