package com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources

import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.VideoWallpaperResponse
import javax.inject.Inject

class VideoRemoteFakeDataSource @Inject constructor() : VideoDataSource {
    override suspend fun getVideos(): List<VideoWallpaperResponse>? {
        return listOf(
            VideoWallpaperResponse(
                image = "https://plus.unsplash.com/premium_photo-1683299266036-9e9b6c9d9152?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80",
                id = 1,
                url = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
            )
        )
    }
}