package com.ameerhamza.animatedgiflivewallpapers.homePage.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.CombineDataSource
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.WallpaperRequest
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.WallpaperResponse
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.servies.VideoWallpaperService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WallpaperRepository @Inject constructor(
    private val videoApiService: VideoWallpaperService,
    private val coroutineScope: CoroutineScope
) {

    private val TAG = "WallpaperRepository"

    fun getWallpapersWithPaging(
        wallpaperRequest: WallpaperRequest
    ): Flow<PagingData<WallpaperResponse>> {

        val config = PagingConfig(
            pageSize = 40,
            prefetchDistance = 0,
        )

        val combineDataSource = CombineDataSource(
            videoApiService, wallpaperRequest
        )

        return Pager(config) {
            combineDataSource
        }.flow
    }

}
