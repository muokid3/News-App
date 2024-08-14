package com.dm.berxley.newsapp.data.remote

import com.dm.berxley.newsapp.data.remote.dto.NewsResponseDto
import com.dm.berxley.newsapp.util.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {


    @GET("everything")
    suspend fun getNews(
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponseDto

    companion object{
        const val BASE_URL = "https://newsapi.org/v2/"
        const val API_KEY = "d15cbfa82693405e87bad11f5a75c299"
    }
}