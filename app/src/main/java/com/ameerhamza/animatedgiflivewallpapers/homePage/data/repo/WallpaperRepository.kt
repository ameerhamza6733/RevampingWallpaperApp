package com.ameerhamza.animatedgiflivewallpapers.homePage.data.repo

import androidx.paging.*
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.CombineDataSource
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.VideoRemotePagingSource
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.VideoWallpaperRequest
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.WallpaperResponse
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.servies.VideoWallpaperService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WallpaperRepository @Inject constructor(private val videoApiService: VideoWallpaperService, private val coroutineScope: CoroutineScope) {

    private val TAG = "WallpaperRepository"

    fun getVideosWithPaging(
        videoWallpaperRequest: VideoWallpaperRequest
    ): Flow<PagingData<WallpaperResponse>> {

        val config = PagingConfig(
            pageSize = 40,
            prefetchDistance = 1,
        )

        val videoRemoteDataSource = CombineDataSource(
            videoApiService
        )

        return Pager(config) {
            videoRemoteDataSource
        }.flow
    }

    companion object {
        val DEFAULT_VIDEO_WALLPAPER_REMOTE_SOURCE = VideoRemotePagingSource.VIDEO_WALLPAPER_PIXEL
    }
}
