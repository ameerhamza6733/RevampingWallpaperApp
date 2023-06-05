package com.ameerhamza.animatedgiflivewallpapers.homePage.ui

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.paging.compose.collectAsLazyPagingItems
import com.ameerhamza.animatedgiflivewallpapers.homePage.ui.componets.toolbar
import com.ameerhamza.animatedgiflivewallpapers.homePage.ui.componets.wallPaperList

@Composable
fun homeScreen(viewModel: HomeScreenViewModel) {
    ConstraintLayout(modifier = Modifier.fillMaxHeight().fillMaxWidth()) {
        val (toolbarRef, tabView, content) = createRefs()
        val toolbarEndingTopContrat = createGuidelineFromTop(0.07f)

        toolbar(modifier = Modifier
            .constrainAs(toolbarRef) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                bottom.linkTo(toolbarEndingTopContrat)
                height = Dimension.fillToConstraints
            })

        wallPaperList(
            viewModel.getWallpapers().collectAsLazyPagingItems(),
            modifier = Modifier.constrainAs(content) {
                top.linkTo(toolbarRef.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                height = Dimension.fillToConstraints

            })

    }
}