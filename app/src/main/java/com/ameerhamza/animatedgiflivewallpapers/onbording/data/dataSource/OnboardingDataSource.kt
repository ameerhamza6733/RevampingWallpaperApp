package com.ameerhamza.animatedgiflivewallpapers.onbording.data.dataSource

import com.ameerhamza.animatedgiflivewallpapers.onbording.data.model.OnboardingItem

interface OnboardingDataSource {
    suspend fun fetchOnboardingItems(): List<OnboardingItem>
}