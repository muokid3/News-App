package com.dm.berxley.newsapp.data.remote.dto

import com.dm.berxley.newsapp.domain.models.Article

data class NewsResponseDto(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)