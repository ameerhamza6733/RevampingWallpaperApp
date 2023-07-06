package com.ameerhamza.animatedgiflivewallpapers.onbording.data.dataSource

import com.ameerhamza.animatedgiflivewallpapers.onbording.data.model.OnboardingItem

interface OnboardDataSource {
    suspend fun fetchOnboardItems(): List<OnboardingItem>
}