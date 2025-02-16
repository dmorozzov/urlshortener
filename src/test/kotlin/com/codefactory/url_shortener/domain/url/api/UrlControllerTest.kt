package com.codefactory.url_shortener.domain.url.api

import com.codefactory.url_shortener.domain.url.model.UrlRecord
import com.codefactory.url_shortener.domain.url.service.UrlService
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath

@WebMvcTest(UrlController::class)
internal class UrlControllerTest {

    @MockitoBean
    lateinit var urlService: UrlService

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `createShortUrl should return shortened url`() {
        // given
        val originalUrl = "originalUrl"
        val createRequest = UrlCreateRequest(originalUrl)
        val record = UrlRecord(id = 1L, shortUrl = "shortUrl", originalUrl = originalUrl)
        `when`(urlService.createRecord(originalUrl)).thenReturn(record)

        // then
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/short-url")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSON<Any>(createRequest))
        )
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(jsonPath("$.shortUrl").value(record.shortUrl))
            .andExpect(jsonPath("$.originalUrl").value(originalUrl))
    }

    @Test
    fun `createShortUrl should return BadRequest  when original url is not provided`() {
        // given
        val createRequest = UrlCreateRequest(null)
        // then
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/short-url")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSON<Any>(createRequest))
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(jsonPath("$.errorCode").value("Validation failed"))
    }


    @Throws(JsonProcessingException::class)
    private fun <T> toJSON(request: T): String {
        return ObjectMapper().writeValueAsString(request)
    }
}
