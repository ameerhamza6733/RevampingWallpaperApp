package com.ameerhamza.animatedgiflivewallpapers.onbording.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ameerhamza.animatedgiflivewallpapers.onbording.data.model.Settings

@Dao
interface OnBordingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSettings(settings: Settings)

    @Query("SELECT * FROM settings LIMIT 1")
    suspend fun getSettings(): Settings?
}