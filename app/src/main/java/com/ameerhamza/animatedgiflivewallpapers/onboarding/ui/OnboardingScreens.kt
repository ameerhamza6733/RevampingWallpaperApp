package com.ameerhamza.animatedgiflivewallpapers.ui.screen

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.ameerhamza.animatedgiflivewallpapers.homePage.ui.MainViewModel
import com.ameerhamza.animatedgiflivewallpapers.onboarding.data.model.OnboardingItem
import com.ameerhamza.animatedgiflivewallpapers.onboarding.ui.OnboardingScreen

/**
 * TODO: Consider using Accompanist library for pager and pager-indicators
 * This is good video with sample Splash/Onboarding application:
 * Also uses preferencesDataStore to save onboarding complete flag 
 * https://www.youtube.com/watch?v=6dRwaXH2cYA
 *
 * Always best to hoist event handling so onComplete provided and ViewModel NOT passed in
*/

@ExperimentalAnimationApi
@Composable
fun OnboardingScreens(onboardingItems: List<OnboardingItem>, onComplete: () -> Unit) {
    Log.d("Calls", "OnboardingScreens composable")

    val currentOnboardingIndex = remember { mutableStateOf(0) }
    val previousePage = remember { mutableStateOf(0) }


    if (onboardingItems.isNotEmpty() && currentOnboardingIndex.value < onboardingItems.size) {
        val currentItem = onboardingItems[currentOnboardingIndex.value]
        val isFirstScreen = currentOnboardingIndex.value == 0

            OnboardingScreen(
                onboardingItem = currentItem,
                buttonText = if (currentOnboardingIndex.value == onboardingItems.size - 1) "Finish" else "Next",
                onButtonClick = {
                    if (currentOnboardingIndex.value == onboardingItems.size - 1) {
                        onComplete()
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

