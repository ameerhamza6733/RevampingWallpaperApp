package com.ameerhamza.animatedgiflivewallpapers.onboarding.data.repository

import com.ameerhamza.animatedgiflivewallpapers.common.data.AppDatabase
import com.ameerhamza.animatedgiflivewallpapers.onboarding.data.dataSource.FakeCongfigDataSource
import com.ameerhamza.animatedgiflivewallpapers.onboarding.data.model.OnboardingItem
import com.ameerhamza.animatedgiflivewallpapers.onboarding.data.model.Settings
import javax.inject.Inject

class OnboardingRepository @Inject constructor(private val appDatabase: AppDatabase)  {
     val dataSource = FakeCongfigDataSource()
     suspend fun insertSettings(settings: Settings) {
       appDatabase.onBordingDao().insertSettings(settings)
    }

     suspend fun getSettings(): Settings? {
        return  appDatabase.onBordingDao().getSettings()
    }

    suspend fun fetchOnboardingItems(): List<OnboardingItem> {
        return dataSource.fetchOnboardingItems()
    }

}
