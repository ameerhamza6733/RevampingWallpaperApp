package com.ameerhamza.animatedgiflivewallpapers.comman.ui


import androidx.compose.ui.graphics.Color

val String.color
    get() = Color(android.graphics.Color.parseColor(this))