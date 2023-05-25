package com.ameerhamza.animatedgiflivewallpapers.onboarding.ui


import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.ameerhamza.animatedgiflivewallpapers.Roboto
import com.ameerhamza.animatedgiflivewallpapers.common.ui.component.PageIndicator
import com.ameerhamza.animatedgiflivewallpapers.common.ui.component.RoundedButton
import com.ameerhamza.animatedgiflivewallpapers.onboarding.data.model.ImageDescription
import com.ameerhamza.animatedgiflivewallpapers.onboarding.data.model.OnboardingItem

@Composable
fun Title(
    onboardingItem: OnboardingItem,
    modifier: Modifier = Modifier
) {
    Text(
        text = onboardingItem.title,
        style = TextStyle(
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Roboto,
            textAlign = TextAlign.Center
        ),
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
fun SubTitle(
    onboardingItem: OnboardingItem,
    modifier: Modifier = Modifier
) {
    Text(
        text = onboardingItem.subtitle,
        style = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = Roboto,
            textAlign = TextAlign.Center,
            color = Color(0xFFFFFFE5)
        ),
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
fun ImageWithText(
    onboardingImage: ImageDescription,
    modifier: Modifier = Modifier
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = onboardingImage.imageResId),
            contentDescription = "",
            modifier = Modifier
        )
        if (onboardingImage.text1.isNotEmpty()) {
            Column {
                Text(
                    text = onboardingImage.text1,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = Roboto,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(start = 8.dp)
                )
                Text(
                    text = onboardingImage.text2,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = Roboto,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@Composable
fun OnboardingImages(
    onboardingItem: OnboardingItem,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        for (i in onboardingItem.image.indices) {
            ImageWithText(onboardingItem.image[i])
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun PrivacyText(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val privacyPolicyText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color(0x66FFFFFF))) {
            append("By continuing you agree to our ")
        }
        withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
            append("Privacy Policy")
        }
        withStyle(style = SpanStyle(color = Color(0x66FFFFFF))) {
            append(" And ")
        }
        withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
            append("Terms of Service")
        }
    }

    Text(
        text = privacyPolicyText,
        style = TextStyle(
            fontSize = 12.sp,
            fontFamily = Roboto,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        ),
        modifier = modifier
    )

}

@Composable
fun OnboardingButton(
    buttonText: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    RoundedButton(
        text = buttonText,
        onClick = onButtonClick,
        modifier = modifier
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun OnboardingScreen(
    onboardingItem: OnboardingItem,
    buttonText: String,
    onButtonClick: () -> Unit,
    isFirstScreen: Boolean,
    currentPage: Int,
    previousePage: Int,
    pageCount: Int,
    onSwipeLeft: () -> Unit,
    onSwipeRight: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val fullWidth = with(LocalDensity.current) {
        configuration.screenWidthDp.dp.toPx().toInt()
    }



    ConstraintLayout(
        modifier = Modifier.fillMaxHeight()
    ) {
        val textMainTitle = createRef()
        val textSubTitle = createRef()
        val layoutImageContainer = createRef()
        val topGuildLine = createGuidelineFromTop(0.08f)
        val continueButtonTopGuildline = createGuidelineFromTop(0.80f)
        val continueButton = createRef()
        val privacyTextView = createRef()
        val pageIndicator = createRef()

        AnimatedContent(
            targetState = onboardingItem, transitionSpec = {
                if (currentPage > previousePage) {
                    slideInHorizontally(initialOffsetX = { fullWidth }) + fadeIn() with
                            slideOutHorizontally(targetOffsetX = { -fullWidth }) + fadeOut()
                } else {
                    slideInHorizontally(initialOffsetX = { -fullWidth }) + fadeIn() with
                            slideOutHorizontally(targetOffsetX = { fullWidth }) + fadeOut()
                }.using(SizeTransform(clip = false))
            },
            modifier = Modifier.padding(start = 24.dp, end = 24.dp)
                .constrainAs(textMainTitle) {
                    start.linkTo(parent.start)
                    top.linkTo(topGuildLine)
                    end.linkTo(parent.end)
                }
        ) {
            Title(onboardingItem)
        }

        AnimatedContent(
            targetState = onboardingItem, transitionSpec = {
                if (currentPage > previousePage) {
                    slideInHorizontally(initialOffsetX = { fullWidth }) + fadeIn() with
                            slideOutHorizontally(targetOffsetX = { -fullWidth }) + fadeOut()

                } else {
                    slideInHorizontally(initialOffsetX = { -fullWidth }) + fadeIn() with
                            slideOutHorizontally(targetOffsetX = { fullWidth }) + fadeOut()
                }.using(SizeTransform(clip = false))
            }, modifier = Modifier.padding(top = 16.dp, start = 24.dp, end = 24.dp)
                .constrainAs(textSubTitle) {
                    top.linkTo(textMainTitle.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            SubTitle(onboardingItem)
        }


        var offsetX by remember { mutableStateOf<Float>(0f) }

        ConstraintLayout(
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 37.dp)
                .constrainAs(layoutImageContainer) {
                    top.linkTo(textSubTitle.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(continueButtonTopGuildline)
                    height = Dimension.fillToConstraints
                }
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectDragGestures(onDrag = { change, dragAmount ->
                        offsetX += dragAmount.x

                    }, onDragCancel = {

                    }, onDragEnd = {
                        if (offsetX < -100) {
                            onSwipeLeft()
                        } else if (offsetX > 100) {
                            onSwipeRight()
                        }
                        // Reset the offsetX after the drag ends
                        offsetX = 0f
                    }, onDragStart = {
                        offsetX = 0f
                    })
                }
        ) {


            AnimatedContent(
                targetState = onboardingItem, transitionSpec = {
                    if (currentPage > previousePage) {
                        slideInHorizontally(initialOffsetX = { fullWidth }) + fadeIn() with
                                slideOutHorizontally(targetOffsetX = { -fullWidth }) + fadeOut()

                    } else {
                        slideInHorizontally(initialOffsetX = { -fullWidth }) + fadeIn() with
                                slideOutHorizontally(targetOffsetX = { fullWidth }) + fadeOut()
                    }.using(SizeTransform(clip = false))
                }, modifier = Modifier.constrainAs(layoutImageContainer) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
            ) {
                OnboardingImages(onboardingItem)
            }
        }

        PageIndicator(
            currentPage = currentPage,
            pageCount = pageCount,
            modifier = Modifier.padding(bottom = 36.dp)
                .constrainAs(pageIndicator) {
                bottom.linkTo(continueButtonTopGuildline)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )


        OnboardingButton(
            buttonText = buttonText,
            onButtonClick = onButtonClick,
            modifier = Modifier.constrainAs(continueButton) {
                top.linkTo(continueButtonTopGuildline)
            }
        )
        AnimatedVisibility(
            visible = isFirstScreen,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.constrainAs(privacyTextView) {
                top.linkTo(continueButton.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }.fillMaxWidth()
        ) {
            PrivacyText(modifier = Modifier
                .clickable {
                    // Handle click action for "Privacy Policy" or "Terms of Service"
                }.constrainAs(privacyTextView) {
                    top.linkTo(continueButton.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }.fillMaxWidth()
                .padding(horizontal = 24.dp)
            ) {

            }
        }

    }

}

