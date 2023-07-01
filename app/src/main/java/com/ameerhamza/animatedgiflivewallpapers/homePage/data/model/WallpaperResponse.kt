package com.ameerhamza.animatedgiflivewallpapers.homePage.data.model

class WallpaperResponse (val image:String, val id:Long, val url:String, val wallpaperType:String) {
    companion object{
        val IMAGE_WALLPAPER ="image_wallpaper"
        val VIDEO_WALLPAPER = "video_wallpaper"
    }
}