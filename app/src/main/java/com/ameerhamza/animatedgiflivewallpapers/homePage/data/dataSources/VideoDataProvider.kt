package com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources

import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.VideoWallpaperPixelsApiResponse

class VideoDataProvider(private val item: VideoWallpaperPixelsApiResponse.VideoWallpaperPixelsVideoListResponse) :
    MediaDataProvider {
    override var image: String = ""
        get() {return item.image}
    override var url: String = ""
        get() {return item.url}
}