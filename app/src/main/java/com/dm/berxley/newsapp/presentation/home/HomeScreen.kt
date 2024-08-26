@file:OptIn(ExperimentalFoundationApi::class)

package com.dm.berxley.newsapp.presentation.home

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dm.berxley.newsapp.R
import com.dm.berxley.newsapp.presentation.Dimens.ExtraSmallPadding
import com.dm.berxley.newsapp.presentation.Dimens.ExtraSmallPadding2
import com.dm.berxley.newsapp.presentation.Dimens.MediumPadding1
import com.dm.berxley.newsapp.presentation.common.ArticleCardShimmerEffect
import com.dm.berxley.newsapp.presentation.common.SearchBar
import com.dm.berxley.newsapp.presentation.home.components.ArticleCard
import com.dm.berxley.newsapp.presentation.navgraph.Screen
import com.google.gson.Gson
import com.google.gson.GsonBuilder

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    newsViewModel: HomeViewModel,
    navHostController: NavHostController
) {
//    val newsViewModel = hiltViewModel<HomeViewModel>()
    val homeState = newsViewModel.homeState.collectAsState().value
    val context = LocalContext.current

    Scaffold { paddingValues ->

        Column(modifier = Modifier.padding(paddingValues)) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = null,
                modifier = Modifier
                    .width(150.dp)
                    .height(30.dp)
                    .padding(horizontal = MediumPadding1)
            )

            Spacer(modifier = Modifier.height(MediumPadding1))

            SearchBar(
                modifier = Modifier.padding(horizontal = MediumPadding1),
                text = "",
                readOnly = true,
                onValueChange = {},
                onClick = {
                    navHostController.navigate(Screen.SearchScreen.route)
                },
                onSearch = {}
            )

            Spacer(modifier = Modifier.height(MediumPadding1))

            Text(
                text = "Headlines here",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = MediumPadding1)
                    .basicMarquee(), fontSize = 12.sp,
                color = colorResource(id = R.color.placeholder)
            )

            Spacer(modifier = Modifier.height(MediumPadding1))


            if (homeState.isLoading) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ShimmerEffect()
                }

            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = MediumPadding1),
                    verticalArrangement = Arrangement.spacedBy(MediumPadding1),
                    contentPadding = PaddingValues(all = ExtraSmallPadding2)
                ) {
                    items(homeState.newsList.size) { index ->
                        homeState.newsList[index]?.let { article ->
                            ArticleCard(article = homeState.newsList[index]) {
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

@Composable
fun ShimmerEffect() {
    Column(verticalArrangement = Arrangement.spacedBy(MediumPadding1)) {
        repeat(10) {
            ArticleCardShimmerEffect(modifier = Modifier.padding(horizontal = MediumPadding1))
        }
    }
}