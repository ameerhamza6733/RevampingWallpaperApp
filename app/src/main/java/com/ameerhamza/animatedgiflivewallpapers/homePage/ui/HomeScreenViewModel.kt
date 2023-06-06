package com.ameerhamza.animatedgiflivewallpapers.homePage.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.ameerhamza.animatedgiflivewallpapers.comman.repository.BillingRepository
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.MediaType
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.VideoWallpaperRequest
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.WallpaperUi
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.repo.VideoRepository
import com.ameerhamza.animatedgiflivewallpapers.homePage.state.MainScreenState
import com.ameerhamza.animatedgiflivewallpapers.onbording.data.repository.OnboardingRepository
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
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
    var dismissSplash = false

    fun getWallpapers(): Flow<PagingData<WallpaperUi>> {

        Log.d(TAG, "loading the data")
        return videoRepository.getVideosWithPaging(
            VideoRepository.DEFAULT_VIDEO_WALLPAPER_REMOTE_SOURCE,
            VideoWallpaperRequest("Nature")
        ).map { pagingData ->
            pagingData.map {
                WallpaperUi(
                    thumbnail = it.image,
                    duration = 0,
                    url = it.url,
                    MediaType.VIDEO
                )
            }
        }.cachedIn(viewModelScope)
    }

    fun startup(context: Context) {
        viewModelScope.launch {
            var onboardingComplete = onboardingRepository.isOnboardingCompleted().await()
            Log.d("Calls", "onboardingComplete = $onboardingComplete")
            if (!onboardingComplete) {
                fetchOnboardingData().await()
                Log.d("Calls", "returned from fetchOnboardingData()")
            } else {
                onboardingComplete = true
            }
            initializeAds(context, onboardingComplete)
        }
    }

    private fun initializeAds(context: Context, onboardingComplete: Boolean) {
        Log.d("Calls", "Initializing MobileAds")
        MobileAds.initialize(context) {
            Log.d("Calls", "MobileAdds completed initialization")
            BillingRepository.initialize(context)
            dismissSplash = true

            mainScreenState.value =
                if (onboardingComplete) MainScreenState.Home
                else MainScreenState.Onboarding(onboardingRepository.onboardingItems)
        }
    }
    fun onboardingCompleted() {
        viewModelScope.launch(Dispatchers.IO) {
            onboardingRepository.saveOnboardingCompleted()
        }
        mainScreenState.value = MainScreenState.Home
    }

    private suspend fun fetchOnboardingData(): Deferred<Boolean> = viewModelScope.async {
        Log.d("Calls", "fetchOnboardingData called")
        viewModelScope.launch(Dispatchers.IO) {
            onboardingRepository.fetchOnboardingItems()
            Log.d(
                "Calls",
                "onboarding items retrieved: ${onboardingRepository.onboardingItems.size}"
            )
        }

        return@async true
    }

    companion object {
        private val TAG = " HomeScreenViewModelTAG"
    }
}
