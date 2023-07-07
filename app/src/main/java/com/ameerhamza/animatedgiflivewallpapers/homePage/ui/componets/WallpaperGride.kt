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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.paging.compose.LazyPagingItems
import com.ameerhamza.animatedgiflivewallpapers.R
import com.ameerhamza.animatedgiflivewallpapers.comman.ui.color
import com.ameerhamza.animatedgiflivewallpapers.comman.ui.theme.yallowColorEC9718
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.WallpaperType
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.WallpaperUi
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage



@Composable
fun WallPaperList(wallpaperList: LazyPagingItems<WallpaperUi>, modifier: Modifier) {

    LazyVerticalGrid(modifier=modifier,
        columns = GridCells.Fixed(3)
        ) {
        items(wallpaperList.itemCount, key = { index ->
            wallpaperList.get(index)!!.thumbnail
        }) { index ->
            WallpaperView(wallpaperList[index]!!)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun WallpaperView(wallpaperUi: WallpaperUi) {

    ConstraintLayout {
        val ( spaceFirst, thumbnail, icon, wallpaperTypeIcon, wallpaperTitle) = createRefs()

        Spacer(modifier = Modifier.padding(top = 8.dp).constrainAs(spaceFirst) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })
        GlideImage(contentScale = ContentScale.Fit,
            model = wallpaperUi.thumbnail,
            contentDescription = null,
            modifier = Modifier.clip(RoundedCornerShape(8.dp))
                .constrainAs(thumbnail) {
                    top.linkTo(spaceFirst.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
        )

        PremiumIcon(painterResource(id = R.drawable.ic_baseline_attach_money_24),
            modifier = Modifier
                .clip(CircleShape)
                .background(yallowColorEC9718)
                .constrainAs(icon) {
                    top.linkTo(parent.top, 8.dp)
                    start.linkTo(parent.start, 8.dp)
                })

        PremiumIcon(painterResource(id = getIconTypeFrom(mediaType = wallpaperUi.wallpaperType)),
            modifier = Modifier
                .clip(CircleShape)
                .background("#3D222222".color)
                .constrainAs(wallpaperTypeIcon) {
                    top.linkTo(parent.top, 8.dp)
                    end.linkTo(parent.end,8.dp)
                })

    }

}

private fun getIconTypeFrom(mediaType: WallpaperType):Int{
   return when(mediaType){
        WallpaperType.VIDEO->{
            R.drawable.video_icon
        }
       WallpaperType.GIF->{
           R.drawable.ic_baseline_gif_24
       }
       WallpaperType.IMAGE->{
           R.drawable.ic_baseline_image_24
       }

    }
}