package com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.VideoWallpaperPixelsApiResponse
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.servies.VideoWallpaperService
import javax.inject.Inject

class VideoRemotePagingSource (private val videoApiService: VideoWallpaperService, private val query:String) :
    PagingSource<Int, MediaDataProvider>() {

//class VideoRemotePagingSource (private val videoApiService: VideoWallpaperService) : PagingSource<Int, MediaDataProvider>() {
    override fun getRefreshKey(state: PagingState<Int, MediaDataProvider>): Int? {
       return null
    }

    override suspend fun load(
        params: LoadParams<Int>
    ): PagingSource.LoadResult<Int, MediaDataProvider> {
       return try {
            val page = params.key?:1
            val reponse = videoApiService.getVideos(perPage= params.loadSize,
                query = query,
            page = page,
            orientation = "portrait")

            PagingSource.LoadResult.Page(
//                data = reponse.videos,
                data = reponse.videos.map{VideoDataProvider(it)},

                prevKey = if (page > 1) page - 1 else null,
                nextKey = if (reponse.nextPage != null) page + 1 else null
            )
        }catch (e:Exception){
            e.printStackTrace()
            PagingSource.LoadResult.Error(e)
        }
    }

    companion object{
        val VIDEO_WALLPAPER_PIXEL= 0
    }
}