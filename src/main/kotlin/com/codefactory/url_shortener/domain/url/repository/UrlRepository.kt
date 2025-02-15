package com.codefactory.url_shortener.domain.url.repository

import org.springframework.data.repository.CrudRepository

interface UrlRepository : CrudRepository<UrlEntity, Long>
