package com.ameerhamza.animatedgiflivewallpapers.homePage.ui

import android.content.Context
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ameerhamza.animatedgiflivewallpapers.homePage.state.MainScreenState
import androidx.paging.map
import com.ameerhamza.animatedgiflivewallpapers.comman.AppPrefs
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.VideoWallpaperRequest
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.VideoWallpaperUi
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.repo.VideoRepository
import com.ameerhamza.animatedgiflivewallpapers.onbording.data.repository.OnboardingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val videoRepository: VideoRepository,
    val onboardingRepository: OnboardingRepository,
    val appPrefs : AppPrefs
) :
    ViewModel() {

    var mainScreenState = MutableStateFlow<MainScreenState>(MainScreenState.Splash)
    var dismissSplash = false

    fun getVideos() : Flow<PagingData<VideoWallpaperUi>> = videoRepository.getVideosWithPaging(VideoRepository.DEFAULT_VIDEO_WALLPAPER_REMOTE_SOURCE,
        VideoWallpaperRequest("Nature")
    ).map { pagingData->
        pagingData.map { VideoWallpaperUi(thumbnail = it.image, duration = 0, videoUrl = it.url) }
    }.cachedIn(viewModelScope)

    fun startup() {
        if (!appPrefs.isOnboardingCompleted)
            fetchOnboardingData()
        else {
            dismissSplash = true
            mainScreenState.value = MainScreenState.Home
        }
    }

    fun onboardingCompleted() {
        viewModelScope.launch {
            appPrefs.saveOnboardingCompleted()
        }
        mainScreenState.value = MainScreenState.Home
    }

    private fun fetchOnboardingData() {
        Log.d("Calls", "fetchOnboardingData called")
        viewModelScope.launch(Dispatchers.IO) {
            onboardingRepository.fetchOnboardingItems()
            dismissSplash = true
            Log.d("Calls", "onboarding items retrieved: ${onboardingRepository.onboardingItems.size}")
            mainScreenState.value = MainScreenState.Onboarding(onboardingRepository.onboardingItems)
        }
    }
}
