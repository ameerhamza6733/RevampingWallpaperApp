package com.ameerhamza.animatedgiflivewallpapers.splash

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.ameerhamza.animatedgiflivewallpapers.Roboto


@Composable
fun AnimatedTextView(text: String) {
    Column {
        val animatedProgress = remember { Animatable(0f) }
        LaunchedEffect(text) {
            animatedProgress.animateTo(
                targetValue = (text.length+1).toFloat(),
                animationSpec = tween(
                    durationMillis = 500,
                    easing = LinearEasing
                )
            )
        }
        Text(
            buildAnnotatedString {
                text.forEachIndexed { index, char ->
                    val alpha = animatedProgress.value
                    val color: Color by animateColorAsState(
                        targetValue = if (index < alpha) Color.White else Color.Transparent
                    )
                    withStyle(
                        style = SpanStyle(color = color, fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Roboto,
                            )
                    ) {
                        append(char)
                    }
                }
            },
            modifier = Modifier,
            fontSize = 20.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAnimatedTextView() {

}
