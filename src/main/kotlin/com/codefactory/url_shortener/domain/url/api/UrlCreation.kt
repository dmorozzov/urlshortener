package com.codefactory.url_shortener.domain.url.api

import com.codefactory.url_shortener.domain.url.model.UrlRecord
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

@Schema(description = "Short url create payload")
data class UrlCreateRequest(
    @field:Schema(
        description = "Original url",
        example = "https://www.google.com/",
        type = "string"
    )
    @field:NotBlank
    val originalUrl: String?
)

data class UrlCreateResponse(
    val shortUrl: String,
    val originalUrl: String
) {
    companion object {
        fun fromUrlRecord(urlRecord: UrlRecord): UrlCreateResponse =
            UrlCreateResponse(shortUrl = urlRecord.shortUrl, originalUrl = urlRecord.originalUrl)
    }
}