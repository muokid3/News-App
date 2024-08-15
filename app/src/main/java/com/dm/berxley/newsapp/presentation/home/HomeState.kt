package com.dm.berxley.newsapp.presentation.home

import com.dm.berxley.newsapp.domain.models.Article

data class HomeState(
    val isLoading: Boolean = false,
    val newsList: List<Article> = emptyList(),
    val newsSourcesList: List<String> = listOf("wired","bbc-news","ab-news","google-news"),
    )
