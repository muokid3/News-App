package com.dm.berxley.newsapp.domain.repositories

import androidx.paging.PagingData
import com.dm.berxley.newsapp.domain.models.Article
import com.dm.berxley.newsapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNews(sources: List<String>): Flow<Resource<List<Article>>>
    fun searchNews(searchQuery: String, sources: List<String>): Flow<Resource<List<Article>>>

    suspend fun upsertArticle(article: Article)
    suspend fun deleteArticle(article: Article)
    fun getArticles(): Flow<List<Article>>
}