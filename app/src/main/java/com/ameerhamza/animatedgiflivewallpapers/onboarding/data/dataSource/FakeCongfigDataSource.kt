package com.ameerhamza.animatedgiflivewallpapers.onboarding.data.dataSource

import com.ameerhamza.animatedgiflivewallpapers.onboarding.data.model.ImageDescription
import com.ameerhamza.animatedgiflivewallpapers.onboarding.data.model.OnboardingItem
import com.ameerhamza.animatedgiflivewallpapers.R

class FakeCongfigDataSource : OnboardingDataSource {
    override suspend fun fetchOnboardingItems(): List<OnboardingItem> {
        return listOf(
            OnboardingItem(
                title = "Dynamic Delights \n GIFs & Video Wallpapers.",
                subtitle ="Transform your screen into an animated masterpiece with our mesmerizing collection of GIFs and  Video Wallpapers.",
                image = listOf(
                    ImageDescription(
                        R.drawable.gif_onbording_first_screen,
                        text1 = "GIF",
                        text2 = "Wallpaper "
                    ),
                    ImageDescription(
                        R.drawable.video_icon_onbording_second_screen,
                        text1 = "VIDEO",
                        text2 = "Wallpaper "
                    )
                )
            ), OnboardingItem(
                title = "Change is Good: Multiple Wallpapers.",
                subtitle = "Endless variety: multiple wallpapers, automatic rotation. Elevate your screen's style.",
                image = listOf(ImageDescription(imageResId = R.drawable.select_maltipal_gif_onbording_second_screen, text1 ="" , text2 ="" ))
            ),
            OnboardingItem(
                title = "4K Wallpapers at Your Fingertips.",
                subtitle = "Explore, download, and adorn your screen with breathtaking 4K wallpapers.",
                image = listOf(ImageDescription(imageResId = R.drawable.search_onbording, text1 ="" , text2 ="" ))
            )
        )
    }

}