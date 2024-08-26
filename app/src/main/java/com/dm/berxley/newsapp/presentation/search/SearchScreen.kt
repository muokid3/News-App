package com.dm.berxley.newsapp.presentation.search

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dm.berxley.newsapp.presentation.Dimens
import com.dm.berxley.newsapp.presentation.Dimens.MediumPadding1
import com.dm.berxley.newsapp.presentation.common.SearchBar
import com.dm.berxley.newsapp.presentation.home.HomeViewModel
import com.dm.berxley.newsapp.presentation.home.ShimmerEffect
import com.dm.berxley.newsapp.presentation.home.components.ArticleCard
import com.dm.berxley.newsapp.presentation.navgraph.Screen

@Composable
fun SearchScreen(
    newsViewModel: HomeViewModel,
    navHostController: NavHostController
) {

    val searchViewModel = hiltViewModel<SearchViewModel>()
    val searchState = searchViewModel.searchState.collectAsState().value

    val context = LocalContext.current

    Scaffold { paddingValues ->

        Column(
            modifier = Modifier
                .padding(
                    top = paddingValues.calculateTopPadding() + MediumPadding1,
                    start = MediumPadding1,
                    end = MediumPadding1,
                    bottom = paddingValues.calculateBottomPadding()
                )
                .fillMaxSize()
        ) {
            SearchBar(
                text = searchState.searchQuery,
                readOnly = false,
                onValueChange = {
                    searchViewModel.updateSearchString(it)
                }, onSearch = {
                    searchViewModel.searchNews()
                })

            Spacer(modifier = Modifier.height(MediumPadding1))

            if (searchState.isLoading) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ShimmerEffect()
                }

            } else {
                searchState.newsList?.let {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(MediumPadding1),
                        contentPadding = PaddingValues(all = Dimens.ExtraSmallPadding2)
                    ) {
                        items(searchState.newsList.size) { index ->
                            searchState.newsList[index]?.let { article ->
                                ArticleCard(article = searchState.newsList[index]) {
                                    newsViewModel.setArticle(article)
                                    navHostController.navigate(Screen.DetailsScreen.route)
                                }
                            }
                        }
                    }
                }
            }
        }

    }


}