package com.ameerhamza.animatedgiflivewallpapers.homePage.ui.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.paging.compose.LazyPagingItems
import com.ameerhamza.animatedgiflivewallpapers.R
import com.ameerhamza.animatedgiflivewallpapers.comman.ui.color
import com.ameerhamza.animatedgiflivewallpapers.comman.ui.theme.yallowColorEC9718
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.WallpaperUi
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage



@Composable
fun wallPaperList(wallpaperList: LazyPagingItems<WallpaperUi>, modifier: Modifier) {

    LazyVerticalGrid(modifier=modifier,
        columns = GridCells.Fixed(3)
        ) {
        items(wallpaperList.itemCount, key = { index ->
            wallpaperList.get(index)!!.thumbnail
        }) { index ->
            wallpaperItem(wallpaperList[index]!!)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun wallpaperItem(video: WallpaperUi) {

    ConstraintLayout {
        val ( spaceFirst, thumbnail, icon, wallpaperTypeIcon, wallpaperTitle) = createRefs()
        val startGuideline = createGuidelineFromStart(0.1f)
        val endGuideline = createGuidelineFromEnd(0.1f)
        val topGuideline = createGuidelineFromTop(0.07f)


        Spacer(modifier = Modifier.padding(top = 8.dp).constrainAs(spaceFirst) {
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

        premiumIcon(painterResource(id = R.drawable.ic_baseline_attach_money_24),
            modifier = Modifier
                .clip(CircleShape)
                .background(yallowColorEC9718)
                .constrainAs(icon) {
                    top.linkTo(topGuideline)
                    start.linkTo(startGuideline)
                })

        premiumIcon(painterResource(id = R.drawable.video_icon_onbording_second_screen),
            modifier = Modifier
                .clip(CircleShape)
                .background("#3D222222".color)
                .constrainAs(wallpaperTypeIcon) {
                    top.linkTo(topGuideline)
                    end.linkTo(endGuideline)
                })

    }


}