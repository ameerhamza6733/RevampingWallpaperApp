package com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources

import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.VideoWallpaperResponse

interface VideoDataSource {
    suspend fun getVideos(): List<VideoWallpaperResponse>?
}