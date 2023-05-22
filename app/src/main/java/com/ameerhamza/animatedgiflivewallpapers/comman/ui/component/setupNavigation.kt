package com.ameerhamza.animatedgiflivewallpapers.comman.ui.component


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ameerhamza.animatedgiflivewallpapers.splash.SplashScreen
import com.ameerhamza.animatedgiflivewallpapers.onbording.ui.viewModel.OnboardingViewModel
import com.ameerhamza.animatedgiflivewallpapers.ui.screen.OnboardingScreens


object NavDestination {
    const val SPLASH_SCREEN = "splash"
    const val ONBOARDING_SCREEN = "onboarding"
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun setupNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = NavDestination.SPLASH_SCREEN) {
        composable(NavDestination.SPLASH_SCREEN) {
            val viewModel = hiltViewModel<OnboardingViewModel>()
            SplashScreen(navController,viewModel)
        }
        composable(NavDestination.ONBOARDING_SCREEN) {
            val viewModel = hiltViewModel<OnboardingViewModel>()
            OnboardingScreens(viewModel)
        }
    }
}

fun navigateToOnboarding(navController: NavController) {
    navController.navigate(NavDestination.ONBOARDING_SCREEN) {
        // Pop up to the start destination of the graph to ensure
        // only one instance of the onboarding screen is in the back stack
        popUpTo(NavDestination.SPLASH_SCREEN) {
            inclusive = true
        }
    }
}
