package com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.servies.VideoWallpaperService

class VideoRemotePagingSource(
    private val videoApiService: VideoWallpaperService,
    private val query: String
) :
    PagingSource<Int, MediaDataProvider>() {

    override fun getRefreshKey(state: PagingState<Int, MediaDataProvider>): Int? {
        return null
    }

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, MediaDataProvider> {
        Log.d("Paging", "VideoRemotePagingSource.load() (loadSize=${params.loadSize}")
        return try {
            val page = params.key ?: 1
            val response = videoApiService.getVideos(
                perPage = params.loadSize,
                query = query,
                page = page,
                orientation = "portrait"
            )
            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (response.nextPage != null) page + 1 else null
            Log.d("Paging", "page: $page, prevKey: $prevKey, nextKey: $nextKey")
            LoadResult.Page(
                data = response.videos.map { VideoDataProvider(it) },
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            e.printStackTrace()
            PagingSource.LoadResult.Error(e)
        }
    }

    companion object {
        val VIDEO_WALLPAPER_PIXEL = 0
    }
}