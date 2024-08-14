package com.dm.berxley.newsapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dm.berxley.newsapp.domain.manager.LocalUserManager
import com.dm.berxley.newsapp.presentation.navgraph.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    localUserManager: LocalUserManager
) : ViewModel() {

    var splashCondition by mutableStateOf(true)
        private set


    var startDestination by mutableStateOf(Screen.AppStartNavigator.route)
        private set

    init {
      viewModelScope.launch {
          localUserManager.readAppEntry().onEach { shouldStartFromHomeScreen ->
              if (shouldStartFromHomeScreen) {
                  startDestination = Screen.NewsNavigator.route
              } else {
                  startDestination = Screen.AppStartNavigator.route
              }

              delay(300)

              splashCondition = false

          }.collect()
      }
    }

}