package com.ameerhamza.animatedgiflivewallpapers.comman.di


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

object AppPrefs {

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = "GifLiveWallpaper"
    )

    val ONBOARDING_COMPLETED_KEY = booleanPreferencesKey(name = "onboarding_completed")

}