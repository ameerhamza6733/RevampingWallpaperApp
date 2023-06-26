package com.ameerhamza.animatedgiflivewallpapers.onbording.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.ameerhamza.animatedgiflivewallpapers.comman.di.AppPrefs
import com.ameerhamza.animatedgiflivewallpapers.comman.di.AppPrefs.dataStore
import com.ameerhamza.animatedgiflivewallpapers.onbording.data.dataSource.FakeCongfigDataSource
import com.ameerhamza.animatedgiflivewallpapers.onbording.data.model.OnboardingItem
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class OnboardingRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val coroutineScope: CoroutineScope
) {
    val dataSource = FakeCongfigDataSource()
    var onboardingItems: List<OnboardingItem> = emptyList()


    suspend fun fetchOnboardingItems(): List<OnboardingItem> {
        onboardingItems = dataSource.fetchOnboardingItems()
        return onboardingItems
    }

     suspend fun isOnboardingCompleted(): Deferred<Boolean>  = coroutineScope.async{
         val value=  context.dataStore.data.map { preferences ->
             preferences.get(AppPrefs.ONBOARDING_COMPLETED_KEY) ?: false
         }.first()
         return@async value
    }

    suspend fun saveOnboardingCompleted() =
            context.dataStore.edit { settings ->
                settings[AppPrefs.ONBOARDING_COMPLETED_KEY] = true
            }




}
