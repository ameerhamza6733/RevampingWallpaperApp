package com.ameerhamza.animatedgiflivewallpapers.comman

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class AppPrefs @Inject constructor(@ApplicationContext private val context: Context) {

    private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(
        name = "GifLiveWallpaper"
    )

    companion object{
        val ONBOARDING_COMPLETED_KEY = booleanPreferencesKey(name = "onboarding_completed")
    }

    suspend fun saveOnboardingCompleted(){
        context.dataStore.edit { preferences ->
            preferences[ONBOARDING_COMPLETED_KEY] = true
        }
    }
    // This is called only once at startup with splash screen displayed.
    // Ok to use synchronous version to get this value
    val isOnboardingCompleted : Boolean = runBlocking {
        context.dataStore.data.first().asMap()[ONBOARDING_COMPLETED_KEY] ?: false
    } as Boolean

}