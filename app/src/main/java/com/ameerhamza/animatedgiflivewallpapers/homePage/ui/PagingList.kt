package com.ameerhamza.animatedgiflivewallpapers.homePage.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.VideoWallpaperPixelsApiResponse

@Composable
fun PagingListScreen(viewModel: HomeScreenViewModel) {
    val videos = viewModel.getVideos().collectAsLazyPagingItems()
    LazyColumn {
        items(videos){
            WallpaperItem(it!!)
        }
    }

}

@Composable
fun WallpaperItem(video:VideoWallpaperPixelsApiResponse.VideoWallpaperPixelsVideoListResponse){
    Text(text = video.url)
}