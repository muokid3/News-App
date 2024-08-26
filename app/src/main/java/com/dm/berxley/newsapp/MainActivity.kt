package com.dm.berxley.newsapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.dm.berxley.newsapp.domain.manager.LocalUserManager
import com.dm.berxley.newsapp.presentation.common.BottomNavigationBar
import com.dm.berxley.newsapp.presentation.navgraph.NavGraph
import com.dm.berxley.newsapp.presentation.navgraph.Screen
import com.dm.berxley.newsapp.presentation.onboarding.OnBoardingViewModel
import com.dm.berxley.newsapp.presentation.onboarding.OnboardingScreen
import com.dm.berxley.newsapp.ui.theme.NewsAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        var keepSplashAlive: Boolean
//        runBlocking {
//            delay(1000)
//            keepSplashAlive = false
//        }
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                mainViewModel.splashCondition
            }
        }

        setContent {
            val navController = rememberNavController()

            NewsAppTheme {
                // A surface container using the 'background' color from the theme

                val isSystemInDarkMode = isSystemInDarkTheme()
                val systemController = rememberSystemUiController()

                SideEffect {
                    systemController.setSystemBarsColor(
                        color = if (isSystemInDarkMode) Color.Black else Color.Transparent,
                        darkIcons = !isSystemInDarkMode
                    )
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    Scaffold(
                        bottomBar = {
                            if (mainViewModel.startDestination == Screen.NewsNavigator.route){
                                BottomNavigationBar(
                                    navController = navController,
                                    mainViewModel = mainViewModel
                                )
                            }
                        }
                    ) { paddingValues ->
                        val startDestination = mainViewModel.startDestination
                        NavGraph(
                            startDestination = startDestination,
                            paddingValues = paddingValues,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}
