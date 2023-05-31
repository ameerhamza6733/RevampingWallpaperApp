package com.ameerhamza.animatedgiflivewallpapers.homePage.data.repo

import androidx.paging.*
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.VideoRemotePagingSource
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.VideoWallpaperPixelsApiResponse
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.VideoWallpaperRequest
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.VideoWallpaperResponse
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.servies.VideoWallpaperService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class VideoRepository @Inject constructor(private val videoApiService: VideoWallpaperService) {

    fun getVideosWithPaging(videoWallpaperRequest: VideoWallpaperRequest): Flow<PagingData<VideoWallpaperResponse>> {
        val config = PagingConfig(pageSize = 15)
        val source: PagingSource<Int, VideoWallpaperPixelsApiResponse.VideoWallpaperPixelsVideoListResponse> =
            when (1) {
                0 -> VideoRemotePagingSource(videoApiService, videoWallpaperRequest.searchTerm)
                1 -> VideoRemotePagingSource(videoApiService,videoWallpaperRequest.searchTerm)
                else -> throw IllegalArgumentException("Invalid source index")
            }
        return Pager(config) { source }.flow.map { pagingData-> pagingData.map { pixelVideo->
            VideoWallpaperResponse(image = pixelVideo.image, url = pixelVideo.url, id = pixelVideo.id)
        } }
    }

}
