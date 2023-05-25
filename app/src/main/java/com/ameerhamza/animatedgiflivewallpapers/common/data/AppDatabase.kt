package com.ameerhamza.animatedgiflivewallpapers.common.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ameerhamza.animatedgiflivewallpapers.onboarding.data.OnBordingDao
import com.ameerhamza.animatedgiflivewallpapers.onboarding.data.model.Settings

@Database(entities = [Settings::class], version = 1)
abstract class AppDatabase  : RoomDatabase() {
    abstract fun onBordingDao(): OnBordingDao
}