package com.ameerhamza.animatedgiflivewallpapers.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ameerhamza.animatedgiflivewallpapers.R
import com.ameerhamza.animatedgiflivewallpapers.comman.ui.component.navigateToHomeScreen
import com.ameerhamza.animatedgiflivewallpapers.comman.ui.component.navigateToOnboarding
import com.ameerhamza.animatedgiflivewallpapers.onbording.ui.viewModel.OnboardingViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SplashScreen(
    navController: NavController,
    onboardingViewModel: OnboardingViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    var imageSizeState by remember { mutableStateOf(ImageSizeState.Small) }
    var isImageRendered by remember { mutableStateOf(false) } // Track if the Image is rendered

    val onboardingCompleted by onboardingViewModel.isOnboardingCompleted.observeAsState(false)
    val transition = updateTransition(targetState = imageSizeState, label = "ImageSizeChangeTransition")
    val imageSize by transition.animateDp(
        label = "ImageSizeAnimation",
        transitionSpec = {
            keyframes {
                durationMillis = 2000
                0.dp at 0 with FastOutLinearInEasing
                100.dp at 1000 with LinearOutSlowInEasing
            }
        }
    ) { state ->
        when (state) {
            ImageSizeState.Small -> 0.dp
            ImageSizeState.Large -> 100.dp
        }
    }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            imageSizeState = ImageSizeState.Large
            delay(2000)
            if (onboardingCompleted) {
                navigateToHomeScreen(navController)
            } else {
                navigateToOnboarding(navController)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher),
                contentDescription = "Image",
                modifier = Modifier.size(imageSize)
            )
            LaunchedEffect(imageSizeState) {
                coroutineScope.launch {
                  delay(1500)
                    if (imageSizeState == ImageSizeState.Large) {
                        isImageRendered = true
                    }
                }

            }
        }
        if (isImageRendered) {
            AnimatedTextView(text = "GIFy")
        }
    }
}

enum class ImageSizeState {
    Small, Large
}
