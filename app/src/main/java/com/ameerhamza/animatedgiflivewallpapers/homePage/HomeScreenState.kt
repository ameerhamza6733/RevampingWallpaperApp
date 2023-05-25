package com.ameerhamza.animatedgiflivewallpapers.homePage

import com.ameerhamza.animatedgiflivewallpapers.onboarding.data.model.OnboardingItem

sealed class MainScreenState {
    object Splash : MainScreenState()
    class  Onboarding(val items: List<OnboardingItem>) : MainScreenState()
    object Home: MainScreenState()
}