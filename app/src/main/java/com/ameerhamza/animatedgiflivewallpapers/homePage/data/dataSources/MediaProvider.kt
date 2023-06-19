package com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources

interface MediaProvider {
    suspend fun load(
        query: String,
        page: Int,
        itemsPerPage: Int
    ): List<MediaDataProvider>
}

