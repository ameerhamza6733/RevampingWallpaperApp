package com.ameerhamza.animatedgiflivewallpapers.comman.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoroutineScopeModule {
    @Provides
    @Singleton
    fun provideCoroutineScope(): CoroutineScope = CoroutineScope(Dispatchers.IO)
}