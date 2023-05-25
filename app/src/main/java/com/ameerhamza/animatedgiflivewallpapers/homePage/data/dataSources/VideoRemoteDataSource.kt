package com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources

import com.ameerhamza.animatedgiflivewallpapers.common.data.exceptions.ApiException
import com.ameerhamza.animatedgiflivewallpapers.common.data.exceptions.NetworkException
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.VideoWallpaperResponse
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.servies.VideoWallpaperService
import javax.inject.Inject

class VideoRemoteDataSource @Inject constructor(private val videoApiService: VideoWallpaperService):VideoDataSource {

    override suspend fun getVideos(): List<VideoWallpaperResponse>? {
        return try {
            val response = videoApiService.getVideos("Cars")
            if (response.isSuccessful) {
                response.body()?.videos?.map { VideoWallpaperResponse(image = it.image, id = it.id, url = it.url) } ?: emptyList()
            } else {
                throw ApiException("API request failed with status code ${response.code()}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw NetworkException("Failed to fetch videos", e)
        }
    }
}


