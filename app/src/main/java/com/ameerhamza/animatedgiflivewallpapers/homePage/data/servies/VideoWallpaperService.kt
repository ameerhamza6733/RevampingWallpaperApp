package com.ameerhamza.animatedgiflivewallpapers.homePage.data.servies

import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.VideoWallpaperPixelsApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface VideoWallpaperService {
    @GET("search")
    suspend fun getVideos(
        @Query("query") query: String
    ): retrofit2.Response<VideoWallpaperPixelsApiResponse>
}

