package com.codefactory.url_shortener.domain.url.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
class InternalShortenerException(message: String?) : RuntimeException(message)
