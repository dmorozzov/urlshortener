package com.codefactory.url_shortener.domain.url.api

import com.codefactory.url_shortener.domain.url.service.UrlService
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class UrlController(
    private val urlService: UrlService
) {
    @Operation(summary = "Obtain short url for provided long url")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201", description = "Successfully created",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = UrlCreateResponse::class)
                )]
            )
        ]
    )
    @PostMapping("/short-url")
    @ResponseStatus(HttpStatus.CREATED)
    fun createUrlRecord(@RequestBody @Valid urlCreateRequest: UrlCreateRequest): UrlCreateResponse {
        val urlRecord = urlService.createRecord(urlCreateRequest.originalUrl!!)
        return UrlCreateResponse.fromUrlRecord(urlRecord)
    }
}