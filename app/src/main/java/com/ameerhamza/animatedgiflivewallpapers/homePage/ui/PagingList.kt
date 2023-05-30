package com.ameerhamza.animatedgiflivewallpapers.homePage.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.MediaDataProvider
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.VideoDataProvider
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.VideoWallpaperPixelsApiResponse
import kotlinx.coroutines.flow.Flow

@Composable
fun PagingListScreen(flowPagingData: Flow<PagingData<VideoDataProvider>>) {
    val items = flowPagingData.collectAsLazyPagingItems()
    LazyColumn {
        items(items){item ->
            item?.let{WallpaperItem(it)}
        }
    }
}

@Composable
fun WallpaperItem(mediaDataProvider: MediaDataProvider){
    Text(text = mediaDataProvider.label())
}