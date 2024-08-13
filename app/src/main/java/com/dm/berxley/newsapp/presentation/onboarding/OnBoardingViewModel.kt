package com.dm.berxley.newsapp.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dm.berxley.newsapp.domain.manager.LocalUserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val localUserManager: LocalUserManager
): ViewModel() {

    fun onEvent(event: OnboardingEvent){
        when(event){
            OnboardingEvent.SaveAppEntry -> {
                saveAppEntry()
            }
        }
    }

    private fun saveAppEntry() {
        viewModelScope.launch {
            localUserManager.saveAppEntry()
        }
    }

}