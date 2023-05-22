package com.ameerhamza.animatedgiflivewallpapers.comman.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ameerhamza.animatedgiflivewallpapers.onbording.data.OnBordingDao
import com.ameerhamza.animatedgiflivewallpapers.onbording.data.model.Settings

@Database(entities = [Settings::class], version = 1)
abstract class AppDatabase  : RoomDatabase() {
    abstract fun onBordingDao(): OnBordingDao
}