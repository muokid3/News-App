package com.dm.berxley.newsapp.presentation.home

import com.dm.berxley.newsapp.domain.models.Article
import com.dm.berxley.newsapp.domain.models.Source

data class HomeState(
    val isLoading: Boolean = false,
    val newsList: List<Article> = emptyList(),
    val bookmarkedArticles: List<Article> = emptyList(),
    val newsSourcesList: List<String> = listOf("wired", "bbc-news", "ab-news", "google-news"),
    val selectedArticle: Article? = null,
    val isBookmarked: Boolean = false,
)
