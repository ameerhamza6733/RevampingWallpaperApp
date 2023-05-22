package com.ameerhamza.animatedgiflivewallpapers.onbording.data.repository

import com.ameerhamza.animatedgiflivewallpapers.comman.data.AppDatabase
import com.ameerhamza.animatedgiflivewallpapers.onbording.data.model.Settings
import javax.inject.Inject

class OnboardingRepository @Inject constructor(private val appDatabase: AppDatabase)  {
     suspend fun insertSettings(settings: Settings) {
       appDatabase.onBordingDao().insertSettings(settings)
    }

     suspend fun getSettings(): Settings? {
        return  appDatabase.onBordingDao().getSettings()
    }
}
