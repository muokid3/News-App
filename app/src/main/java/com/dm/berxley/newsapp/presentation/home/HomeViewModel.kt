package com.dm.berxley.newsapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dm.berxley.newsapp.domain.repositories.NewsRepository
import com.dm.berxley.newsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {
    var homeState = MutableStateFlow(HomeState())
        private set

    init {
        getNews()
    }

    private fun getNews() {
        viewModelScope.launch {
            //set status to loading
            homeState.update {
                it.copy(isLoading = true)
            }

            //get the news from api through repository
            newsRepository.getNews(sources = homeState.value.newsSourcesList)
                .collectLatest { result ->
                    when (result) {
                        is Resource.Error -> {
                            homeState.update {
                                it.copy(isLoading = false)
                            }
                        }

                        is Resource.Loading -> {
                            homeState.update {
                                it.copy(isLoading = result.isLoading)
                            }
                        }

                        is Resource.Success -> {
                            result.data?.let { listResults ->
                                homeState.update {
                                    it.copy(isLoading = false, newsList = listResults)
                                }
                            }
                        }
                    }
                }
        }
    }
}