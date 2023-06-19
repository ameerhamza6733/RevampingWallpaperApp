package com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState

class MixedMediaPagingSource(private val query: String, private val mediaProviders: List<MediaProvider>) : PagingSource<Int, MediaDataProvider>() {

    override fun getRefreshKey(state: PagingState<Int, MediaDataProvider>): Int? {
        return null
    }

    override suspend fun load(
        params: LoadParams<Int>
    ) : LoadResult<Int, MediaDataProvider> {
        Log.d("Paging", "MixedMediaPagingSource.load() (loadSize=${params.loadSize}")

        val page = params.key ?: 1
        val prevKey = if (page > 1) page - 1 else null
        val nextKey = page + 1
        val dataItems: MutableList<MediaDataProvider> = mutableListOf()
        mediaProviders.forEach{mediaProvider ->
            Log.d("Paging", "calling load on $mediaProvider")
            dataItems += mediaProvider.load(query, page, params.loadSize)
        }
        Log.d("Paging", "MixedMediaPagingSource.load() returning ${dataItems.size} load results")
        return LoadResult.Page(
            data = dataItems,
            prevKey = prevKey,
            nextKey = nextKey
        )
    }
}