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
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ameerhamza.animatedgiflivewallpapers.comman.Utils
import com.ameerhamza.animatedgiflivewallpapers.comman.ui.theme.MyApplicationTheme
import com.ameerhamza.animatedgiflivewallpapers.homePage.state.MainScreenState
import com.ameerhamza.animatedgiflivewallpapers.homePage.ui.HomeScreenViewModel
import com.ameerhamza.animatedgiflivewallpapers.homePage.ui.wallPaperList
import com.ameerhamza.animatedgiflivewallpapers.ui.screen.OnboardingScreens
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
                    viewModel.startup()
                    showCurrentScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun showCurrentScreen(viewModel: HomeScreenViewModel) {
    val state by viewModel.mainScreenState.collectAsState()

    Log.d("Calls", "showMainScreen with $state")
    // App always starts with Splash screen which is the startDestination in NavController
    when (state) {
        is MainScreenState.Onboarding -> {
            OnboardingScreens((state as MainScreenState.Onboarding).items){
                viewModel.onboardingCompleted()
            }
        }
        is MainScreenState.Home -> {
            wallPaperList(viewModel)
        }
    }
}




