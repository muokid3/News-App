package com.dm.berxley.newsapp.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.dm.berxley.newsapp.presentation.home.HomeScreen
import com.dm.berxley.newsapp.presentation.onboarding.OnBoardingViewModel
import com.dm.berxley.newsapp.presentation.onboarding.OnboardingScreen

@Composable
fun NavGraph(
    startDestination: String
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
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
            startDestination = Screen.NewsNavigation.route,
            route = Screen.NewsNavigator.route
        ) {
            composable(route = Screen.NewsNavigation.route) {
                HomeScreen()
            }
        }
    }

}