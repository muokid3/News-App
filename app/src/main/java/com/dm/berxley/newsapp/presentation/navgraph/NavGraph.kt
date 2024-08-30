package com.dm.berxley.newsapp.presentation.navgraph

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.dm.berxley.newsapp.domain.models.Article
import com.dm.berxley.newsapp.presentation.bookmark.BookmarkScreen
import com.dm.berxley.newsapp.presentation.details.DetailsScreen
import com.dm.berxley.newsapp.presentation.home.HomeScreen
import com.dm.berxley.newsapp.presentation.home.HomeViewModel
import com.dm.berxley.newsapp.presentation.onboarding.OnBoardingViewModel
import com.dm.berxley.newsapp.presentation.onboarding.OnboardingScreen
import com.dm.berxley.newsapp.presentation.search.SearchScreen
import com.google.gson.Gson
import com.google.gson.GsonBuilder

@Composable
fun NavGraph(
    startDestination: String,
    paddingValues: PaddingValues,
    navController: NavController
) {


    val newsViewModel = hiltViewModel<HomeViewModel>()

    NavHost(
        navController = navController as NavHostController,
        startDestination = startDestination,
        modifier = Modifier.padding(paddingValues)
    ) {
        navigation(
            startDestination = Screen.OnBoardingScreen.route,
            route = Screen.AppStartNavigator.route
        ) {
            composable(route = Screen.OnBoardingScreen.route) {
                val viewModel = hiltViewModel<OnBoardingViewModel>()
                OnboardingScreen(event = viewModel::onEvent)
            }

        }

        navigation(
            startDestination = Screen.HomeScreen.route,
            route = Screen.NewsNavigator.route
        ) {
            composable(route = Screen.HomeScreen.route) {
                HomeScreen(newsViewModel = newsViewModel, navController)
            }

            composable(route = Screen.SearchScreen.route) {
                SearchScreen(newsViewModel = newsViewModel, navController)
            }

            composable(route = Screen.BookmarkScreen.route) {
                BookmarkScreen(newsViewModel = newsViewModel, navController)
            }

            composable(route = Screen.DetailsScreen.route) {
                DetailsScreen(newsViewModel = newsViewModel, navHostController = navController)
            }
        }
    }

}