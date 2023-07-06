package com.ameerhamza.animatedgiflivewallpapers.homePage.ui.componets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun PremiumIcon(painter: Painter, modifier: Modifier) {
        Box(modifier = modifier.padding(4.dp).size(20.dp).clip(RoundedCornerShape(7.dp))) {
            Icon(painter, contentDescription = null)
        }


}
