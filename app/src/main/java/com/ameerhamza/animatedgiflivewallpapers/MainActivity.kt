package com.ameerhamza.animatedgiflivewallpapers

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ameerhamza.animatedgiflivewallpapers.common.ui.component.NavDestination
import com.ameerhamza.animatedgiflivewallpapers.common.ui.component.setupNavigation
import com.ameerhamza.animatedgiflivewallpapers.common.ui.theme.MyApplicationTheme
import com.ameerhamza.animatedgiflivewallpapers.homePage.MainScreenState
import com.ameerhamza.animatedgiflivewallpapers.homePage.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchVideos()
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()

                    setupNavigation(navController, NavDestination.SPLASH_SCREEN, viewModel)
                    viewModel.fetchOnboardingData()
                    showMainScreen(viewModel, navController)
                }
            }
        }
    }
}

@Composable
fun showMainScreen(viewModel: MainViewModel, navigator: NavController) {
    val state by viewModel.mainScreenState.collectAsState()

    Log.d("Calls", "showMainScreen with $state")
    // App always starts with Splash screen which is the startDestination in NavController
    when (state) {
        is MainScreenState.Onboarding -> {
            navigator.popBackStack() // Onboarding only called with Splash displayed
            navigator.navigate(NavDestination.ONBOARDING_SCREEN)
        }
        is MainScreenState.Home -> {
            navigator.navigate(NavDestination.HOME_SCREEN)
        }
    }
}




