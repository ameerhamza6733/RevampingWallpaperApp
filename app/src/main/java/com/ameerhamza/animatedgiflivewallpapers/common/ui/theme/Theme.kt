package com.ameerhamza.animatedgiflivewallpapers.common.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = buttonColor,
    primaryVariant = Purple700,
    secondary = Teal200,
    background = backgroundDark
)

private val LightColorPalette = lightColors(
    primary = backgroundDark,
    primaryVariant = Purple700,
    secondary = Teal200
    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {


    MaterialTheme(
        colors = DarkColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}