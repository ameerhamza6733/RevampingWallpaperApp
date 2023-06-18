package com.ameerhamza.animatedgiflivewallpapers.homePage.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.ameerhamza.animatedgiflivewallpapers.comman.di.AppPrefs
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.MediaDataProvider
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.VideoRemotePagingSource
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.tvshowcharacters.CharactersRemotePagingSource
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.tvshowcharacters.DuckDuckGoCharactersApiService
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.tvshowcharacters.ShowCharacterServiceModule
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.MediaType
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.VideoWallpaperRequest
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.WallpaperUi
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.repo.CharacterRepository
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.repo.VideoRepository
import com.ameerhamza.animatedgiflivewallpapers.homePage.state.MainScreenState
import com.ameerhamza.animatedgiflivewallpapers.onbording.data.repository.OnboardingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
//    private val videoRepository: VideoRepository,
    val onboardingRepository: OnboardingRepository
) :
    ViewModel() {

    var mainScreenState = MutableStateFlow<MainScreenState>(MainScreenState.Splash)
    var dismissSplash = false

    @Inject
    lateinit var videoRepository: VideoRepository

    @Inject
    lateinit var charactersRepository: CharacterRepository

    fun getWallpapers(): Flow<PagingData<WallpaperUi>> {
        return getMediaFromVideoSource()
//        return getMediaFromPhotoSourceWithPaging()

    }

    private fun getMediaFromPhotoSourceWithPaging(): Flow<PagingData<WallpaperUi>> {
        Log.d(TAG, "loading the data")

        val config = PagingConfig(
            pageSize = 40,
            prefetchDistance = 0,
        )

        var retrofit = ShowCharacterServiceModule.provideRetrofitForDuckDuckGoShow()
        var apiService = retrofit.create(DuckDuckGoCharactersApiService::class.java)

        val charactersRemoteDataSource = CharactersRemotePagingSource(
            apiService
        )

        return Pager(config) {
            charactersRemoteDataSource
        }.flow.map { pagingData ->
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

    private fun getMediaFromVideoSource(): Flow<PagingData<WallpaperUi>> {
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

    fun startup() {
        viewModelScope.launch {
            if (!onboardingRepository.isOnboardingCompleted().await())
                fetchOnboardingData()
            else {
                dismissSplash = true
                mainScreenState.value = MainScreenState.Home
            }
        }
    }

    fun onboardingCompleted() {
        viewModelScope.launch(Dispatchers.IO) {
            onboardingRepository.saveOnboardingCompleted()
        }
        mainScreenState.value = MainScreenState.Home
    }

    private fun fetchOnboardingData() {
        Log.d("Calls", "fetchOnboardingData called")
        viewModelScope.launch(Dispatchers.IO) {
            onboardingRepository.fetchOnboardingItems()
            dismissSplash = true
            Log.d(
                "Calls",
                "onboarding items retrieved: ${onboardingRepository.onboardingItems.size}"
            )
        }
    }

    companion object {
        private val TAG = " HomeScreenViewModelTAG"
    }
}
