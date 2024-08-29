package com.dm.berxley.newsapp.presentation.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.RoomDatabase
import com.dm.berxley.newsapp.data.local.NewsDao
import com.dm.berxley.newsapp.domain.models.Article
import com.dm.berxley.newsapp.domain.repositories.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val newsRoomDao: NewsDao
) : ViewModel() {

    private val _articleState = MutableStateFlow(BookMarkState())
    val articleState = _articleState.asStateFlow()

    init {
        getArticles()
    }

    private fun getArticles() {
        viewModelScope.launch {
            newsRoomDao.getArticles().collectLatest { articlesList ->
                _articleState.update {
                    it.copy(articles = articlesList)
                }
            }
        }
    }
}