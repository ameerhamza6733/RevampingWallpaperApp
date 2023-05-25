package com.ameerhamza.animatedgiflivewallpapers.onboarding.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ameerhamza.animatedgiflivewallpapers.onboarding.data.model.Settings

@Dao
interface OnBordingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSettings(settings: Settings)

    @Query("SELECT * FROM settings LIMIT 1")
    suspend fun getSettings(): Settings?
}