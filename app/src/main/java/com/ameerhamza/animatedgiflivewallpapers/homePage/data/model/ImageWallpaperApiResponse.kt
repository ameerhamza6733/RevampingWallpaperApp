package com.ameerhamza.animatedgiflivewallpapers.homePage.data.model

class ImageWallpaperApiResponse(val nextPage:Int?, val images:List<ImageWallpaper>)
class ImageWallpaper(val lowResImageUrl: String, val imageUrl: String, val title: String)