package com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.tvshowcharacters

import com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.tvshowcharacters.model.Cast
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiInterface {
    @GET("")
    suspend fun getCast(
        @Url url: String,
    ): Response<Cast>
}