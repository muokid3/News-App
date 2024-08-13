package com.dm.berxley.newsapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.dm.berxley.newsapp.domain.manager.LocalUserManager
import com.dm.berxley.newsapp.presentation.onboarding.OnBoardingViewModel
import com.dm.berxley.newsapp.presentation.onboarding.OnboardingScreen
import com.dm.berxley.newsapp.ui.theme.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var localUserManager: LocalUserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var keepSplashAlive: Boolean
        runBlocking {
            delay(1000)
            keepSplashAlive = false
        }
        installSplashScreen().setKeepOnScreenCondition {
            keepSplashAlive
        }

        lifecycleScope.launch {
            localUserManager.readAppEntry().collectLatest {
                Log.d("Test", it.toString())
            }
        }

        setContent {
            NewsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel = hiltViewModel<OnBoardingViewModel>()

                    OnboardingScreen(event = viewModel::onEvent)
                }
            }
        }
    }
}
