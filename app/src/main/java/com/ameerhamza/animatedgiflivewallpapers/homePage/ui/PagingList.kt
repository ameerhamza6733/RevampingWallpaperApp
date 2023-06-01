package com.ameerhamza.animatedgiflivewallpapers.homePage.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.paging.compose.collectAsLazyPagingItems
import com.ameerhamza.animatedgiflivewallpapers.R
import com.ameerhamza.animatedgiflivewallpapers.comman.ui.color
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.VideoWallpaperUi
import com.ameerhamza.animatedgiflivewallpapers.homePage.ui.componets.premiumIcon
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun wallPaperList(viewModel: HomeScreenViewModel) {
    val wallpaperList = viewModel.getVideos().collectAsLazyPagingItems()
    LazyVerticalGrid(columns = GridCells.Fixed(3)) {
        items(wallpaperList.itemCount, key = { index ->
            wallpaperList.get(index)!!.thumbnail
        }) { index ->
            wallpaperItem(wallpaperList[index]!!)
        }
    }


}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun wallpaperItem(video: VideoWallpaperUi) {

    ConstraintLayout {
        val (spaceFirst,thumbnail, icon,wallpaperTypeIcon) = createRefs()
        Spacer(modifier = Modifier.padding(top = 8.dp).constrainAs(spaceFirst){
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })
        GlideImage(
            model = video.thumbnail,
            contentDescription = null,
            modifier = Modifier.height(200.dp).clip(RoundedCornerShape(8.dp))
                .constrainAs(thumbnail) {
                    top.linkTo(spaceFirst.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
        )

        premiumIcon(painterResource(id = R.drawable.ic_baseline_attach_money_24),modifier = Modifier
            .background("#EC9718".color)
            .padding(4.dp)
            .constrainAs(icon){
            top.linkTo(thumbnail.top)
            start.linkTo(thumbnail.start)
        })

        premiumIcon(painterResource(id = R.drawable.video_icon_onbording_second_screen),modifier = Modifier
            .background("#3D222222".color)
            .constrainAs(wallpaperTypeIcon){
            top.linkTo(thumbnail.top)
            end.linkTo(parent.end)
        })

    }


}