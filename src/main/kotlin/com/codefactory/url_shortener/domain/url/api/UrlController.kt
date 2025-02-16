package com.codefactory.url_shortener.domain.url.api

import com.codefactory.url_shortener.domain.url.service.UrlService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class UrlController(
    private val urlService: UrlService
) {
    @Operation(summary = "Obtain short url for provided url")
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

    @Operation(summary = "Resolve short url and redirect to original url")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "301", description = "Redirect to found long url"),
            ApiResponse(responseCode = "404", description = "Short url not found")]
    )
    @GetMapping("/short-url/{shortUrl}")
    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
    fun resolveShortUrl(@NotBlank @PathVariable shortUrl: String, response: HttpServletResponse) {
        val urlRecord = urlService.findUrlByShortUrl(shortUrl)
        response.status = HttpServletResponse.SC_MOVED_PERMANENTLY;
        response.setHeader("Location", urlRecord.originalUrl);
        response.setHeader("Connection", "close");
    }
}
