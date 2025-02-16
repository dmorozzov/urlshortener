package com.codefactory.url_shortener.domain.url.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

class UrlEncoderTest {

    @TestFactory
    fun testEncode() = listOf(
        5L to "5",
        61L to "Z",
        62L to "10",
        124L to "20",
        150L to "2q"
    )
        .map { (input, expected) ->
            DynamicTest.dynamicTest("encode $input to base62 gives $expected") {
                assertEquals(expected, UrlEncoder.encode(input))
            }
        }

    @TestFactory
    fun testDecode() = listOf(
        "5" to 5L,
        "Z" to 61L,
        "10" to 62L,
        "20" to 124L,
        "2q" to 150L
    )
        .map { (input, expected) ->
            DynamicTest.dynamicTest("decode $input to base10 gives $expected") {
                assertEquals(expected, UrlEncoder.decode(input))
            }
        }
}
