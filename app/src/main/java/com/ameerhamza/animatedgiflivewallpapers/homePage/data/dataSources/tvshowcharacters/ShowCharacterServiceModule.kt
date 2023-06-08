package com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.tvshowcharacters

import com.ameerhamza.animatedgiflivewallpapers.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
object ShowCharacterServiceModule {
    private const val BaseUrl = BuildConfig.SHOW_API_BASE_URL
    fun provideRetrofitForDuckDuckGoShow(): Retrofit {
        var mOkHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BASIC)
            )
            .build()

        return Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(mOkHttpClient)
            .build()
    }
}