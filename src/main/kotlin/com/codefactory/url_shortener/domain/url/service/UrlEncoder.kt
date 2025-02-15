package com.codefactory.url_shortener.domain.url.service

import kotlin.math.pow

class UrlEncoder {
    companion object {

        private const val BASE = 62
        private const val CHARACTERS: String = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

        fun encode(input: Long): String {
            var number = input
            val resultBuilder = StringBuilder(1)
            do {
                resultBuilder.insert(0, CHARACTERS[(number % BASE).toInt()])
                number /= BASE.toLong()
            } while (number > 0)
            return resultBuilder.toString()
        }

        fun decode(input: String): Long {
            var result = 0L
            val length = input.length - 1
            for (i in 0..length) {
                result += BASE.toDouble().pow(i.toDouble()).toLong() * CHARACTERS.indexOf(input[length - i])
            }
            return result
        }
    }
}
