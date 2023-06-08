package com.ameerhamza.animatedgiflivewallpapers.homePage.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.MediaDataProvider
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.VideoRemotePagingSource
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.VideoWallpaperRequest
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.VideoWallpaperResponse
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.servies.VideoWallpaperService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class VideoRepository @Inject constructor(private val videoApiService: VideoWallpaperService) : MediaRepository {

    fun getVideosWithPaging(
        dataSourceType: Int,
        videoWallpaperRequest: VideoWallpaperRequest
    ): Flow<PagingData<MediaDataProvider>> {

        val config = PagingConfig(
            pageSize = 40,
            prefetchDistance = 0,
        )

       val videoRemoteDataSource = VideoRemotePagingSource(
            videoApiService,
            videoWallpaperRequest.searchTerm
        )

        return Pager(config) {
            videoRemoteDataSource
        }.flow
    }

    companion object {
        val DEFAULT_VIDEO_WALLPAPER_REMOTE_SOURCE = VideoRemotePagingSource.VIDEO_WALLPAPER_PIXEL
    }

    override fun fetchPagedItems(): Pager<String, MediaDataProvider> {
        TODO("Not yet implemented")
    }
}
