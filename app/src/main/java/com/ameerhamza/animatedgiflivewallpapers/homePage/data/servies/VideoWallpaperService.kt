package com.ameerhamza.animatedgiflivewallpapers.homePage.data.servies

import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.VideoWallpaperPixelsApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface VideoWallpaperService {
    @GET("search")
    suspend fun getVideos(
        @Query("per_page") perPage:Int,
        @Query("query") query: String,
        @Query("page") page:Int,
        @Query("orientation") orientation:String,
    ): VideoWallpaperPixelsApiResponse
}

