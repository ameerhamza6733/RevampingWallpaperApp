package com.ameerhamza.animatedgiflivewallpapers.onboarding.data.model

data class OnboardingItem(
    val title: String,
    val subtitle: String,
    val image: List<ImageDescription>
)

data class ImageDescription(
    val imageResId: Int,
    val text1: String,
    val text2: String
)