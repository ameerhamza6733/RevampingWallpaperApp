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
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ameerhamza.animatedgiflivewallpapers.comman.ui.component.NavDestination
import com.ameerhamza.animatedgiflivewallpapers.comman.ui.component.setupNavigation
import com.ameerhamza.animatedgiflivewallpapers.comman.ui.theme.MyApplicationTheme
import com.ameerhamza.animatedgiflivewallpapers.homePage.state.MainScreenState
import com.ameerhamza.animatedgiflivewallpapers.homePage.ui.HomeScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<HomeScreenViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        splashScreen.setKeepOnScreenCondition { !viewModel.dismissSplash }
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    val navController = rememberNavController()

                    setupNavigation(navController, NavDestination.ONBOARDING_SCREEN, viewModel)
                    viewModel.startup()
                    showCurrentScreen(viewModel, navController)
                }
            }
        }
    }
}

@Composable
fun showCurrentScreen(viewModel: HomeScreenViewModel, navigator: NavController) {
    val state by viewModel.mainScreenState.collectAsState()

    Log.d("Calls", "showMainScreen with $state")
    when (state) {
        is MainScreenState.Onboarding -> {
            navigator.popBackStack()
            navigator.navigate(NavDestination.ONBOARDING_SCREEN)
        }
        is MainScreenState.Home -> {
            navigator.navigate(NavDestination.HOME_SCREEN)
        }
    }
}




