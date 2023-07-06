package com.ameerhamza.animatedgiflivewallpapers.homePage.ui.componets


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.ameerhamza.animatedgiflivewallpapers.R
import com.ameerhamza.animatedgiflivewallpapers.comman.ui.color
import com.ameerhamza.animatedgiflivewallpapers.comman.ui.theme.buttonColor

@Composable
fun Toolbar(modifier: Modifier) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
    ) {
        val (drawerButton, forYouTab, fromGalleryTab, searchButton) = createRefs()
        val middleGuidleine = createGuidelineFromStart(0.5f)
        val startGuideline = createGuidelineFromStart(0.04f)
        val endGuideline = createGuidelineFromStart(0.94f)

        IconButton(modifier = Modifier
            .constrainAs(drawerButton) {
                start.linkTo(startGuideline)
                top.linkTo(parent.top)
            }, onClick = {
        }) {
            Icon(
                painter = painterResource(R.drawable.ic_baseline_notes_24),
                contentDescription = null
            )
        }
        roundedRectangleWithLeftCorner(modifier = Modifier.constrainAs(forYouTab) {
            end.linkTo(fromGalleryTab.start)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            height = Dimension.wrapContent

        })
        RoundedRectangleWithRightCorner(modifier = Modifier.constrainAs(fromGalleryTab) {
            start.linkTo(middleGuidleine)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            height = Dimension.wrapContent

        })
        IconButton(modifier = Modifier.constrainAs(searchButton) {
            top.linkTo(parent.top)
            end.linkTo(endGuideline)
        }, onClick = {
        }) {
            Icon(
                painter = painterResource(R.drawable.ic_baseline_search_24),
                contentDescription = null
            )
        }
    }
}

@Composable
fun roundedRectangleWithLeftCorner(modifier: Modifier) {
    Box(
        modifier = modifier.size(116.dp, 32.dp)

    ) {
        Canvas(modifier = Modifier) {
            val cornerRadius = CornerRadius(36f, 36f)
            val path = Path().apply {
                addRoundRect(
                    RoundRect(
                        rect = Rect(
                            offset = Offset(0f, 0f),
                            size = Size(116.dp.toPx(), 32.dp.toPx()),
                        ),
                        bottomLeft = cornerRadius,
                        topLeft = cornerRadius
                    )
                )
            }

            drawPath(path, color = buttonColor)
        }
        Text("For you", modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun RoundedRectangleWithRightCorner(modifier: Modifier) {
    Box(
        modifier = modifier.size(116.dp, 32.dp)

    ) {
        Canvas(modifier = Modifier) {
            val cornerRadius = CornerRadius(36f, 36f)
            val path = Path().apply {
                addRoundRect(
                    RoundRect(
                        rect = Rect(
                            offset = Offset(0f, 0f),
                            size = Size(116.dp.toPx(), 32.dp.toPx()),
                        ),
                        bottomRight = cornerRadius,
                        topRight = cornerRadius
                    )
                )
            }

            drawPath(path, color = "#004A50DA".color)
        }
        Text("Gallery", modifier = Modifier.align(Alignment.Center))
    }
}


