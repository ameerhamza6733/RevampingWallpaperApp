package com.ameerhamza.animatedgiflivewallpapers.ui.screen

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.ameerhamza.animatedgiflivewallpapers.onbording.ui.viewModel.OnboardingViewModel
import com.ameerhamza.animatedgiflivewallpapers.onbording.ui.OnboardingScreen

@ExperimentalAnimationApi
@Composable
fun OnboardingScreens(viewModel: OnboardingViewModel) {
    viewModel.fetchOnboardingData()
    val onboardingItems by viewModel.onboardingItems.observeAsState(emptyList())
    val currentOnboardingIndex = remember { mutableStateOf(0) }
    val previousePage = remember { mutableStateOf(0) }


    if (onboardingItems.isNotEmpty() && currentOnboardingIndex.value < onboardingItems.size) {
        val currentItem = onboardingItems[currentOnboardingIndex.value]
        val isFirstScreen = currentOnboardingIndex.value == 0

        if (currentOnboardingIndex.value == onboardingItems.size - 1) {
            viewModel.markOnboardingCompleted()
        }

            OnboardingScreen(
                onboardingItem = currentItem,
                buttonText = if (currentOnboardingIndex.value == onboardingItems.size - 1) "Finish" else "Next",
                onButtonClick = {
                    if (currentOnboardingIndex.value == onboardingItems.size - 1) {
                        // Handle the last onboarding screen button click
                    } else {
                        previousePage.value = currentOnboardingIndex.value
                        currentOnboardingIndex.value++
                    }
                },
                isFirstScreen = isFirstScreen,
                currentPage = currentOnboardingIndex.value,
                pageCount = onboardingItems.size,
                previousePage =  previousePage.value,
                onSwipeLeft = {
                    if (currentOnboardingIndex.value == onboardingItems.size - 1) {
                        // Handle the last onboarding screen swipe right
                    } else {
                        currentOnboardingIndex.value++
                    }
                },
                onSwipeRight = {
                    previousePage.value = currentOnboardingIndex.value
                    currentOnboardingIndex.value--
                }
            )
        }

}

