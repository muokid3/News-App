package com.dm.berxley.newsapp.presentation.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dm.berxley.newsapp.data.local.NewsDao
import com.dm.berxley.newsapp.domain.models.Article
import com.dm.berxley.newsapp.domain.models.Source
import com.dm.berxley.newsapp.domain.repositories.NewsRepository
import com.dm.berxley.newsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val newsRoomDao: NewsDao
) : ViewModel() {
    private val _homeState = MutableStateFlow(HomeState())
    val homeState = _homeState.asStateFlow()


    init {
        getNews()
        getBookmarkedArticles()
    }

    private fun getNews() {
        viewModelScope.launch {
            //set status to loading
            _homeState.update {
                it.copy(isLoading = true)
            }

            //get the news from api through repository
            newsRepository.getNews(sources = homeState.value.newsSourcesList)
                .collectLatest { result ->
                    when (result) {
                        is Resource.Error -> {
                            _homeState.update {
                                it.copy(isLoading = false)
                            }
                        }

                        is Resource.Loading -> {
                            _homeState.update {
                                it.copy(isLoading = result.isLoading)
                            }
                        }

                        is Resource.Success -> {
                            result.data?.let { listResults ->
                                _homeState.update {
                                    it.copy(isLoading = false, newsList = listResults)
                                }
                            }
                        }
                    }
                }
        }
    }

    fun setArticle(article: Article) {
        _homeState.update {
            it.copy(selectedArticle = article)
        }
    }

    fun bookMarkArticle(article: Article) {
        viewModelScope.launch {
            if (_homeState.value.isBookmarked) {

                newsRepository.deleteArticle(article)
                _homeState.update {
                    it.copy(isBookmarked = false)
                }
            } else {

                newsRepository.upsertArticle(article)
                _homeState.update {
                    it.copy(isBookmarked = true)
                }
            }
        }
    }

    private fun getBookmarkedArticles() {
        viewModelScope.launch {
            newsRoomDao.getArticles().collectLatest { articlesList ->
                _homeState.update {
                    it.copy(bookmarkedArticles = articlesList)
                }
            }
        }
    }

    fun checkIfBookmarked() {
        _homeState.value.selectedArticle?.let { selectedArticle ->
            val isBookmarked = homeState.value.bookmarkedArticles.any {
                it.url == selectedArticle.url
            }

            Log.e("BOOKMARKING", "checkIfBookmarked: $isBookmarked")

            _homeState.update {
                it.copy(isBookmarked = isBookmarked)
            }
        }
    }

}