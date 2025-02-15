package com.codefactory.url_shortener.config

import com.codefactory.url_shortener.domain.url.exception.NotFoundException
import org.jboss.logging.Logger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest

@RestControllerAdvice
class ExceptionControllerAdvice {

    private val logger = Logger.getLogger(this::class.java)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(
        exception: MethodArgumentNotValidException,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        return ResponseEntity.badRequest()
            .body(ErrorResponse(exception.message, "Validation failed", exception.bindingResult.allErrors))
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(
        exception: NotFoundException,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        return ResponseEntity.badRequest()
            .body(ErrorResponse(exception.message, "Not found", null))
    }

    @ExceptionHandler(Exception::class)
    fun handleUnexpected(exception: Exception): ResponseEntity<ErrorResponse> {
        logger.error("Unexpected exception: ${exception.message}")
        return ResponseEntity.internalServerError()
            .body(ErrorResponse(exception.message, "Unexpected exception", null))
    }
}

data class ErrorResponse(
    var errorMessage: String?,
    var errorCode: String?,
    var details: List<Any>?
)
