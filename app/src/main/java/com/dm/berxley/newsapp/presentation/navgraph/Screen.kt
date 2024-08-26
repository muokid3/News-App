package com.dm.berxley.newsapp.presentation.navgraph

sealed class Screen(
    val route: String
) {
    object AppStartNavigator: Screen(route = "appStartNavigator")

    object OnBoardingScreen: Screen(route = "onBoardingScreen")




    object NewsNavigator: Screen(route = "newsNavigator")


    object HomeScreen: Screen(route = "homeScreen")
    object SearchScreen: Screen(route = "searchScreen")
    object BookmarkScreen: Screen(route = "bookmarkScreen")
    object DetailsScreen: Screen(route = "detailsScreen")
}