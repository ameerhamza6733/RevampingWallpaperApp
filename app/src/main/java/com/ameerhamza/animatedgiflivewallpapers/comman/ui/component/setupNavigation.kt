package com.ameerhamza.animatedgiflivewallpapers.comman.ui.component


import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ameerhamza.animatedgiflivewallpapers.homePage.ui.HomeScreenViewModel
import com.ameerhamza.animatedgiflivewallpapers.homePage.ui.wallPaperList
import com.ameerhamza.animatedgiflivewallpapers.splash.SplashScreen
import com.ameerhamza.animatedgiflivewallpapers.ui.screen.OnboardingScreens


object NavDestination {
    const val SPLASH_SCREEN = "splash"
    const val ONBOARDING_SCREEN = "onboarding"
    const val HOME_SCREEN ="home_screen"
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun setupNavigation(
    navController: NavHostController,
    startDestination: String,
    mainViewModel: HomeScreenViewModel
) {
    NavHost(navController, startDestination = startDestination) {
        composable(NavDestination.ONBOARDING_SCREEN) {
            Log.d("Calls", "Navigating to OnboardingScreens")
            OnboardingScreens(mainViewModel.onboardingRepository.onboardingItems){
                mainViewModel.onboardingCompleted()
            }
        }
        composable(NavDestination.HOME_SCREEN){
            Log.d("Calls", "Navigating to wallPaperList")
            wallPaperList(mainViewModel)
        }
    }
}