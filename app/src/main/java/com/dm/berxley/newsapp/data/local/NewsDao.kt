package com.dm.berxley.newsapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.dm.berxley.newsapp.domain.models.Article
import kotlinx.coroutines.flow.Flow


@Dao
interface NewsDao {
    @Upsert
    suspend fun upsertArticle(article: Article)

    @Delete
    suspend fun delete(article: Article)

    @Query("SELECT * FROM Article")
    fun getArticles(): Flow<List<Article>>
}