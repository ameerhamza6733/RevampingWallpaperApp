package com.ameerhamza.animatedgiflivewallpapers.homePage.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.VideoWallpaperPixelsApiResponse
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.VideoWallpaperUi
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun PagingListScreen(viewModel: HomeScreenViewModel) {
    val videos = viewModel.getVideos().collectAsLazyPagingItems()
    LazyColumn {
        items(videos){
            WallpaperItem(it!!)
        }
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun WallpaperItem(video:VideoWallpaperUi){
    GlideImage(
        model = video.thumbnail
      , contentDescription = null
    )

}