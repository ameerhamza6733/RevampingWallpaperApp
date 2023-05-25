package com.ameerhamza.animatedgiflivewallpapers.common.ui.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.unit.dp

@Composable
fun PageIndicator(currentPage: Int, pageCount: Int,modifier: Modifier) {
    val selectedWidth = 24.dp
    val unselectedWidth = 12.dp

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier

    ) {
        repeat(pageCount) { index ->
            val width = if (index == currentPage) selectedWidth else unselectedWidth

            Box(
                modifier = Modifier
                    .size(width, height = 6.dp)
                    .animateContentSize( animationSpec = tween(500))
                    .background(
                        color = if (index == currentPage) Color.LightGray else Color.Gray,
                        shape = CircleShape
                    )

            )
        }
    }
}

