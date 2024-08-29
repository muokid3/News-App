package com.dm.berxley.newsapp.data.repository

import com.dm.berxley.newsapp.data.local.NewsDao
import com.dm.berxley.newsapp.data.remote.NewsApi
import com.dm.berxley.newsapp.domain.models.Article
import com.dm.berxley.newsapp.domain.repositories.NewsRepository
import com.dm.berxley.newsapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val newsDao: NewsDao
) : NewsRepository {
    override fun getNews(sources: List<String>): Flow<Resource<List<Article>>> {
        return flow {
            emit(Resource.Loading(true))

            val responseFromApi = try {
                newsApi.getNews(sources.joinToString(separator = ",") { it })
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading news"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading news"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading news"))
                return@flow
            }

            emit(Resource.Success(data = responseFromApi.articles))
            emit(Resource.Loading(false))
            return@flow
        }
    }

    override fun searchNews(
        searchQuery: String,
        sources: List<String>
    ): Flow<Resource<List<Article>>> {
        return flow {
            emit(Resource.Loading(true))

            val responseFromApi = try {
                newsApi.searchNews(
                    searchQuery = searchQuery,
                    sources = sources.joinToString(separator = ",") { it })
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading news"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading news"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading news"))
                return@flow
            }

            emit(Resource.Success(data = responseFromApi.articles))
            emit(Resource.Loading(false))
            return@flow
        }
    }

    override suspend fun upsertArticle(article: Article) {
        newsDao.upsertArticle(article)
    }

    override suspend fun deleteArticle(article: Article) {
        newsDao.delete(article)
    }

    override fun getArticles(): Flow<List<Article>> {
        return newsDao.getArticles()
    }
}