package com.dm.berxley.newsapp.presentation.search

import android.util.Log
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
class SearchViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {
    var searchState = MutableStateFlow(SearchState())
        private set


    fun updateSearchString(searchQuery: String) {
        //update search query
        searchState.update {
            it.copy(searchQuery = searchQuery)
        }

    }

    fun searchNews() {

        if (searchState.value.searchQuery.isNotEmpty()) {
            //set is loading to true
            searchState.update {
                it.copy(isLoading = true)
            }

            //make api call to search
            viewModelScope.launch {
                newsRepository.searchNews(
                    searchState.value.searchQuery,
                    searchState.value.newsSourcesList
                ).collectLatest { result ->
                    when (result) {
                        is Resource.Error -> {
                            searchState.update {
                                it.copy(isLoading = false)
                            }
                        }

                        is Resource.Loading -> {
                            searchState.update {
                                it.copy(isLoading = result.isLoading)
                            }
                        }

                        is Resource.Success -> {
                            result.data?.let { searchResults ->
                                searchState.update {
                                    it.copy(isLoading = false, newsList = searchResults)
                                }
                            }
                        }
                    }
                }
            }
        }
    }


}