package com.dm.berxley.newsapp.presentation.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.RoomDatabase
import com.dm.berxley.newsapp.data.local.NewsDao
import com.dm.berxley.newsapp.domain.models.Article
import com.dm.berxley.newsapp.domain.repositories.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    val newsRepository: NewsRepository,
    private val newsRoomDao: NewsDao
) : ViewModel() {

    var articleState = MutableStateFlow(BookMarkState())
        private set

    init {
        getArticles()
    }

    private fun getArticles() {
        viewModelScope.launch {
            newsRoomDao.getArticles().collectLatest { articlesList ->
                articleState.update {
                    it.copy(articles = articlesList)
                }
            }
        }
    }

}