package com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources

import android.util.Log
import androidx.paging.PagingSource
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.servies.VideoWallpaperService

class VideoMediaProvider(
    private val query: String,
    private val videoApiService: VideoWallpaperService
) : MediaProvider {
    override suspend fun load(
        query: String,
        page: Int,
        itemsPerPage: Int
    ): List<MediaDataProvider>{
        return try {
            Log.d("Paging", "calling videoApiService.getVideos()")
            val response = videoApiService.getVideos(
                perPage = itemsPerPage,
                query = query,
                page = page,
                orientation = "portrait"
            )
            Log.d("Paging", "Returning ${response.videos.size} videos")
            response.videos.map { VideoDataProvider(it) }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}

