package com.dm.berxley.newsapp.presentation.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.dm.berxley.newsapp.MainViewModel
import com.dm.berxley.newsapp.domain.models.BottomNavItem
import com.dm.berxley.newsapp.presentation.navgraph.Screen


val bottomNavItems = listOf<BottomNavItem>(
    BottomNavItem("Home", Icons.Filled.Home, Icons.Outlined.Home),
    BottomNavItem("Search", Icons.Filled.Search, Icons.Outlined.Search),
    BottomNavItem("Bookmark", Icons.Filled.Bookmark, Icons.Outlined.Bookmark)
)

@Composable
fun BottomNavigationBar(
    navController: NavController,
    mainViewModel: MainViewModel
) {

    BottomAppBar {
        bottomNavItems.forEachIndexed { index, bottomNavItem ->
            NavigationBarItem(
                selected = mainViewModel.selectedBottomIndex == index,
                onClick = {
                    when (index) {
                        0 -> {
                            navController.navigate(Screen.HomeScreen.route)
                            mainViewModel.setBottomIndex(0)
                        }

                        1 -> {
                            navController.navigate(Screen.SearchScreen.route)
                            mainViewModel.setBottomIndex(1)
                        }

                        2 -> {
                            navController.navigate(Screen.BookmarkScreen.route)
                            mainViewModel.setBottomIndex(2)
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (mainViewModel.selectedBottomIndex == index) bottomNavItem.selectedIcon else bottomNavItem.unselectedIcon,
                        contentDescription = bottomNavItem.title
                    )
                },
                label = {
                    Text(text = bottomNavItem.title)
                }
            )
        }

    }
}