package com.ameerhamza.animatedgiflivewallpapers.common.di

import android.content.Context
import androidx.room.Room
import com.ameerhamza.animatedgiflivewallpapers.common.data.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseProvider {
        private var database: AppDatabase? = null
    @Singleton
    @Provides
        fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
            synchronized(this) {
                return database ?: buildDatabase(context).also { database = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "app-db").build()
        }
    }

