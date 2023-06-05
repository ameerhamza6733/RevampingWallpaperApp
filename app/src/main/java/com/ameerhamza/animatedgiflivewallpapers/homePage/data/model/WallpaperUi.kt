package com.ameerhamza.animatedgiflivewallpapers.homePage.data.model

class WallpaperUi (val thumbnail:String, val duration:Long, val url:String,val wallpaperType:MediaType)

sealed class MediaType{
    object VIDEO : MediaType()
    object IMAGE : MediaType()
    object GIF : MediaType()
}