package com.ameerhamza.animatedgiflivewallpapers.homePage.data.repo

import androidx.paging.Pager
import androidx.paging.PagingSource
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.MediaDataProvider

interface MediaRepository {
    fun fetchPagedItems() : Pager<String, MediaDataProvider>
}