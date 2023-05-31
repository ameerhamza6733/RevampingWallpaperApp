package com.ameerhamza.animatedgiflivewallpapers.homePage.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.MediaDataProvider
import com.ameerhamza.animatedgiflivewallpapers.homePage.state.MainScreenState
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.repo.VideoRepository
import com.ameerhamza.animatedgiflivewallpapers.onbording.data.repository.OnboardingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class  HomeScreenViewModel @Inject constructor(
    private val videoRepository: VideoRepository,
    val onboardingRepository: OnboardingRepository
) :
    ViewModel() {

    var mainScreenState = MutableStateFlow<MainScreenState>(MainScreenState.Splash)

    fun getItems() : Flow<PagingData<MediaDataProvider>> = videoRepository.fetchPagedItems().flow.cachedIn(viewModelScope)

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
