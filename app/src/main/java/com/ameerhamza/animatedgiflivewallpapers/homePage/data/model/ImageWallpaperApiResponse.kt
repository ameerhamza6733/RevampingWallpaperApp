package com.ameerhamza.animatedgiflivewallpapers.homePage.data.model

class ImageWallpaperApiResponse(val nextPageNumber:Int?,val imageWallpaperListResponse:List<ImageWallpaperListApiResponse>)
class ImageWallpaperListApiResponse(val lowResImageUrl: String, val imageUrl: String, val title: String)