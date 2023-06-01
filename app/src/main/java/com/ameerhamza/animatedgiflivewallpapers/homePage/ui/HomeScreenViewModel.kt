package com.ameerhamza.animatedgiflivewallpapers.homePage.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ameerhamza.animatedgiflivewallpapers.homePage.state.MainScreenState
import androidx.paging.map
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
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val videoRepository: VideoRepository,
    val onboardingRepository: OnboardingRepository
) :
    ViewModel() {

    var mainScreenState = MutableStateFlow<MainScreenState>(MainScreenState.Splash)

    fun getVideos() : Flow<PagingData<VideoWallpaperUi>> = videoRepository.getVideosWithPaging(VideoRepository.DEFAULT_VIDEO_WALLPAPER_REMOTE_SOURCE,
        VideoWallpaperRequest("Nature")
    ).map { pagingData->
        pagingData.map { VideoWallpaperUi(thumbnail = it.image, duration = 0, videoUrl = it.url) }
    }.cachedIn(viewModelScope)

    fun fetchOnboardingData() {
        viewModelScope.launch(Dispatchers.IO) {
            onboardingRepository.fetchOnboardingItems()
            delay(3000) // TODO: Remove. For testing full splash animations only
            mainScreenState.value = MainScreenState.Onboarding(onboardingRepository.onboardingItems)
        }
    }

    fun onboardingCompleted() {
        mainScreenState.value = MainScreenState.Home
    }


    companion object{
        private val TAG =" HomeScreenViewModelTAG"
    }
}
