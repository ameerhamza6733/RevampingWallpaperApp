package com.ameerhamza.animatedgiflivewallpapers.homePage.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ameerhamza.animatedgiflivewallpapers.common.data.Result
import com.ameerhamza.animatedgiflivewallpapers.homePage.MainScreenState
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.VideoWallpaperUi
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.repo.VideoRepository
import com.ameerhamza.animatedgiflivewallpapers.onboarding.data.model.OnboardingItem
import com.ameerhamza.animatedgiflivewallpapers.onboarding.data.repository.OnboardingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val videoRepository: VideoRepository,
    private val onboardingRepository: OnboardingRepository
) :
    ViewModel() {

    private val _videos = MutableLiveData<List<VideoWallpaperUi>>()
    val videos: LiveData<List<VideoWallpaperUi>> = _videos
    var onboardingItems: List<OnboardingItem> = emptyList()

    var mainScreenState = MutableStateFlow<MainScreenState>(MainScreenState.Splash)

    fun fetchVideos() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = videoRepository.getVideos()
            when (result) {
                is Result.Success -> {
                    Log.d(TAG,"Success: ${result.data?.firstOrNull()?.videoUrl}")
                }
                is Result.Error -> {

                    val errorMessage = result.message
                    // Display error message to the user or take appropriate action
                    Log.d(TAG,"errorMessage: ${errorMessage}")

                }
            }
        }
    }
    fun fetchOnboardingData() {
        viewModelScope.launch(Dispatchers.IO) {
            onboardingItems = onboardingRepository.fetchOnboardingItems()
            delay(3000)
            mainScreenState.value = MainScreenState.Onboarding(onboardingItems)
        }
    }

    fun onboardingCompleted() {
        mainScreenState.value = MainScreenState.Home
    }
    companion object{
        private val TAG =" HomeScreenViewModelTAG"
    }
}
