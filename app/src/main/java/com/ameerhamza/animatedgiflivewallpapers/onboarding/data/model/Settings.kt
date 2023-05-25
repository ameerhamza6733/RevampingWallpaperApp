package com.ameerhamza.animatedgiflivewallpapers.onboarding.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class Settings(
    @PrimaryKey val id: Int = 0,
    @ColumnInfo(name = "onboarding_completed") val onboardingCompleted: Boolean = false
)