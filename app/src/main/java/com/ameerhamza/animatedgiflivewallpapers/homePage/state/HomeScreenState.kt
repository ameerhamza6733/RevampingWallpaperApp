package com.ameerhamza.animatedgiflivewallpapers.homePage.state

import com.ameerhamza.animatedgiflivewallpapers.onbording.data.model.OnboardingItem

sealed class MainScreenState {
    object Splash : MainScreenState()
    class  Onboarding(val items: List<OnboardingItem>) : MainScreenState()
    object Home: MainScreenState()
}