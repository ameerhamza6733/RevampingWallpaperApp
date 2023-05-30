package com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources

import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.VideoWallpaperPixelsApiResponse

class VideoDataProvider(private val item: VideoWallpaperPixelsApiResponse.VideoWallpaperPixelsVideoListResponse) : MediaDataProvider {
    override fun label(): String {
        return item.url
    }
}