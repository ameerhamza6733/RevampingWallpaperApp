package com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.VideoWallpaperPixelsApiResponse
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.servies.VideoWallpaperService
import javax.inject.Inject

class VideoRemotePagingSource (private val videoApiService: VideoWallpaperService) : PagingSource<String, MediaDataProvider>() {
    override suspend fun load(
        params: LoadParams<String>
    ): LoadResult<String, MediaDataProvider> {
        return try {

            val postion = (params.key ?: "1")
            val response = videoApiService.getVideos("Cars")
            LoadResult.Page(
                data = response.videos.map{VideoDataProvider(it)},
                prevKey = if (postion=="1")  null else (postion.toInt()-1).toString(),
                nextKey = if (postion.toInt()==response.totalResults) null else (postion.toInt()-1).toString()
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
            // Handle errors in this block and return LoadResult.Error if it is an
            // expected error (such as a network failure).
        }
    }

    override fun getRefreshKey(state: PagingState<String, MediaDataProvider>): String? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.toInt()?.plus(1)?.toString() ?: anchorPage?.nextKey?.toInt()?.minus(1).toString()
        }
    }
}