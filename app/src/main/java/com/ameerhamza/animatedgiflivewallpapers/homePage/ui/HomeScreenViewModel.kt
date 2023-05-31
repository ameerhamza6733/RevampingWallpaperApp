package com.ameerhamza.animatedgiflivewallpapers.homePage.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.ameerhamza.animatedgiflivewallpapers.comman.data.Result
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.VideoWallpaperPixelsApiResponse
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.VideoWallpaperRequest
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.VideoWallpaperUi
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.repo.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val videoRepository: VideoRepository) :
    ViewModel() {


    fun getVideos() : Flow<PagingData<VideoWallpaperUi>> = videoRepository.getVideosWithPaging(
        VideoWallpaperRequest("Nature")
    ).map { pagingData->
        pagingData.map { VideoWallpaperUi(thumbnail = it.image, duration = 0, videoUrl = it.url) }
    }.cachedIn(viewModelScope)


    companion object{
        private val TAG =" HomeScreenViewModelTAG"
    }
}
