package com.ameerhamza.animatedgiflivewallpapers.homePage.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.MediaDataProvider
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.MediaProvider
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.MixedMediaPagingSource
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.PhotoMediaProvider
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.VideoMediaProvider
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.VideoRemotePagingSource
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.servies.VideoWallpaperService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MixedMediaRepository @Inject constructor() : MediaRepository {

    // These should be instantiated/injected dynamically based on settings

    @Inject
    lateinit var videoWallpaperService: VideoWallpaperService

    @Inject
    lateinit var charactersRepository: CharacterRepository


    fun getMediaWithPaging(
        searchTerm: String
    ): Flow<PagingData<MediaDataProvider>> {

        val config = PagingConfig(
            pageSize = 40,
            prefetchDistance = 0,
        )

        val mediaProviders: MutableList<MediaProvider> = mutableListOf()
        mediaProviders.add(VideoMediaProvider(searchTerm, videoWallpaperService))
        mediaProviders.add(PhotoMediaProvider(searchTerm))
        val mediaRemoteDataSource = MixedMediaPagingSource(
            searchTerm,
            mediaProviders
        )

        return Pager(config) {
            mediaRemoteDataSource
        }.flow
    }

    override fun fetchPagedItems(): Pager<String, MediaDataProvider> {
        TODO("Not yet implemented")
    }
}
