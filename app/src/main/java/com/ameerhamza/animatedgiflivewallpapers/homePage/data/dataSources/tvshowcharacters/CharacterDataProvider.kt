package com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.tvshowcharacters

import com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.MediaDataProvider
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.tvshowcharacters.model.Cast
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.tvshowcharacters.model.ShowCharacter
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.VideoWallpaperPixelsApiResponse

class CharacterDataProvider(private val item: ShowCharacter) :
    MediaDataProvider {
    override fun label(): String {
        return item.name
    }
}