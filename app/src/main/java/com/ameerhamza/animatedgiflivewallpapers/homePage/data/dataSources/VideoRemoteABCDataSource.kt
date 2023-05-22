package com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources

import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.VideoWallpaperResponse

class VideoRemoteABCDataSource : VideoDataSource {
    override suspend fun getVideos(): List<VideoWallpaperResponse>? {
        TODO("Not yet implemented")
    }
}