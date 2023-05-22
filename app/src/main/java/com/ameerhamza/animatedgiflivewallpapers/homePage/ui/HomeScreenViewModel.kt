package com.ameerhamza.animatedgiflivewallpapers.homePage.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ameerhamza.animatedgiflivewallpapers.comman.data.Result
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.VideoWallpaperUi
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.repo.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val videoRepository: VideoRepository) :
    ViewModel() {

    private val _videos = MutableLiveData<List<VideoWallpaperUi>>()
    val videos: LiveData<List<VideoWallpaperUi>> = _videos


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


    companion object{
        private val TAG =" HomeScreenViewModelTAG"
    }
}
