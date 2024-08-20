package com.dm.berxley.newsapp.presentation.bookmark

import com.dm.berxley.newsapp.domain.models.Article

data class BookMarkState(
    val articles: List<Article> = emptyList()
)
