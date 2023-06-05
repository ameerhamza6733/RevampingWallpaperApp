package com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.VideoWallpaperPixelsApiResponse
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.servies.VideoWallpaperService
import javax.inject.Inject

class VideoRemotePagingSource (private val videoApiService: VideoWallpaperService, private val query:String) :
    PagingSource<Int, VideoWallpaperPixelsApiResponse.VideoWallpaperPixelsVideoListResponse>() {
    override fun getRefreshKey(state: PagingState<Int, VideoWallpaperPixelsApiResponse.VideoWallpaperPixelsVideoListResponse>): Int? {
       return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VideoWallpaperPixelsApiResponse.VideoWallpaperPixelsVideoListResponse> {
       return try {
            val page = params.key?:1
            val reponse = videoApiService.getVideos(perPage= params.loadSize,
                query = query,
            page = page,
            orientation = "portrait")

            LoadResult.Page(
                data = reponse.videos,
                prevKey = if (page > 1) page - 1 else null,
                nextKey = if (reponse.nextPage != null) page + 1 else null
            )
        }catch (e:Exception){
            e.printStackTrace()
            LoadResult.Error(e)

        }
    }

    companion object{
        val VIDEO_WALLPAPER_PIXEL= 0
    }
}