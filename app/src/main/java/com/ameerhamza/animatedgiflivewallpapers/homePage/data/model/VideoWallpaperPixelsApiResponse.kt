package com.ameerhamza.animatedgiflivewallpapers.homePage.data.model

import com.google.gson.annotations.SerializedName

class VideoWallpaperPixelsApiResponse(
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("prev_page") val prevPage: String?,
    @SerializedName("next_page") val nextPage: String?,
    @SerializedName("videos") val videos: List<VideoWallpaperPixelsVideoListResponse>
) {
    class VideoWallpaperPixelsVideoListResponse(@SerializedName("url") val url: String, @SerializedName("id") val id:Long, @SerializedName("image") val image:String){
        fun toVideoWallpaperResponse():VideoWallpaperResponse{
           return VideoWallpaperResponse(
                image = image,
                url = url,
                id = id
            )
        }
    }
}