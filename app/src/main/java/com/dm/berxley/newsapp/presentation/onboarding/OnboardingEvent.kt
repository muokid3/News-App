package com.dm.berxley.newsapp.presentation.onboarding

sealed class OnboardingEvent {
    object SaveAppEntry: OnboardingEvent()
}