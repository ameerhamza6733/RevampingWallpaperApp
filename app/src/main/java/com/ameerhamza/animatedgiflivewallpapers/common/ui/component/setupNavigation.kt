package com.ameerhamza.animatedgiflivewallpapers.common.ui.component


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ameerhamza.animatedgiflivewallpapers.homePage.ui.HomeScreen
import com.ameerhamza.animatedgiflivewallpapers.homePage.ui.MainViewModel
import com.ameerhamza.animatedgiflivewallpapers.splash.SplashScreen
import com.ameerhamza.animatedgiflivewallpapers.ui.screen.OnboardingScreens


object NavDestination {
    const val SPLASH_SCREEN = "splash"
    const val ONBOARDING_SCREEN = "onboarding"
    const val HOME_SCREEN = "home"
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun setupNavigation(
    navController: NavHostController,
    startDestination: String,
    mainViewModel: MainViewModel
) {
    NavHost(navController, startDestination = startDestination) {
        composable(NavDestination.SPLASH_SCREEN) {
            SplashScreen()
        }
        composable(NavDestination.ONBOARDING_SCREEN) {
            val viewModel = mainViewModel // hiltViewModel<MainViewModel>()
            OnboardingScreens(viewModel.onboardingItems){
                viewModel.onboardingCompleted()
            }
        }

        composable(NavDestination.HOME_SCREEN) {
            val viewModel = mainViewModel // hiltViewModel<MainViewModel>()
            HomeScreen(viewModel)
        }
    }
}
