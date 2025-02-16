package com.codefactory.url_shortener.domain.url.service

import com.codefactory.url_shortener.domain.url.exception.NotFoundException
import com.codefactory.url_shortener.domain.url.model.UrlRecord
import com.codefactory.url_shortener.domain.url.repository.SequenceRepository
import com.codefactory.url_shortener.domain.url.repository.UrlEntity
import com.codefactory.url_shortener.domain.url.repository.UrlRepository
import org.jboss.logging.Logger
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UrlService(
    private val urlRepository: UrlRepository,
    private val sequenceRepository: SequenceRepository
) {
    private val logger = Logger.getLogger(this::class.java)

    @Transactional
    fun createRecord(originalUrl: String): UrlRecord {
        val urlId = sequenceRepository.incrementAndGet()
        val urlEntity = urlRepository.save(
            UrlEntity(
                id = urlId,
                shortUrl = UrlEncoder.encode(urlId),
                originalUrl = originalUrl)
        )
        val urlRecord = UrlRecord.fromUrlEntity(urlEntity)
        logger.info("Url $originalUrl => Short url ${urlRecord.shortUrl}")
        return urlRecord
    }

    fun findUrlByShortUrl(shortUrl: String): UrlRecord {
        val urlId = UrlEncoder.decode(shortUrl)
        val urlEntity = urlRepository.findByIdOrNull(urlId) ?: throw NotFoundException("Short url `$shortUrl` not found.")
        val urlRecord = UrlRecord.fromUrlEntity(urlEntity)
        logger.info("Short url $shortUrl => Url ${urlRecord.originalUrl}")
        return urlRecord
    }
}
