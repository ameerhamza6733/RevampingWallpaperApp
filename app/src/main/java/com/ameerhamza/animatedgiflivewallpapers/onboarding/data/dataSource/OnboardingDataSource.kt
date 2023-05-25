package com.ameerhamza.animatedgiflivewallpapers.onboarding.data.dataSource

import com.ameerhamza.animatedgiflivewallpapers.onboarding.data.model.OnboardingItem

interface OnboardingDataSource {
    suspend fun fetchOnboardingItems(): List<OnboardingItem>
}