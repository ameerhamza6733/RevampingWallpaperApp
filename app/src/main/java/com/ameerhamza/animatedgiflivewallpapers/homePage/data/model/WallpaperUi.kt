package com.ameerhamza.animatedgiflivewallpapers.homePage.data.model

data class WallpaperUi (val thumbnail:String, val duration:Long, val url:String,val wallpaperType:WallpaperType)

sealed class WallpaperType{
    object VIDEO : WallpaperType()
    object IMAGE : WallpaperType()
    object GIF : WallpaperType()
}