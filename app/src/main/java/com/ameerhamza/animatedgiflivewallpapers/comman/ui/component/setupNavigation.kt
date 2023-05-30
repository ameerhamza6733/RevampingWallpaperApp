package com.ameerhamza.animatedgiflivewallpapers.comman.ui.component


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ameerhamza.animatedgiflivewallpapers.homePage.ui.HomeScreenViewModel
import com.ameerhamza.animatedgiflivewallpapers.homePage.ui.PagingListScreen
import com.ameerhamza.animatedgiflivewallpapers.splash.SplashScreen
import com.ameerhamza.animatedgiflivewallpapers.onbording.ui.viewModel.OnboardingViewModel
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
        composable(NavDestination.SPLASH_SCREEN) {
            SplashScreen()
        }
        composable(NavDestination.ONBOARDING_SCREEN) {
            OnboardingScreens(mainViewModel.onboardingRepository.onboardingItems){
                mainViewModel.onboardingCompleted()
            }
        }
        composable(NavDestination.HOME_SCREEN){
            val viewModel = hiltViewModel<HomeScreenViewModel>()
            PagingListScreen(viewModel)
        }
    }
}