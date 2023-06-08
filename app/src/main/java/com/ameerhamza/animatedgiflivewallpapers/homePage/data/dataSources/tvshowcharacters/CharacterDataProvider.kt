package com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.tvshowcharacters

import com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.MediaDataProvider
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.tvshowcharacters.model.Cast
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.tvshowcharacters.model.ShowCharacter
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.VideoWallpaperPixelsApiResponse

class CharacterDataProvider(private val item: ShowCharacter) :
    MediaDataProvider {

    val IMAGE_BASE_URL = "http://duckduckgo.com/";
    override var url: String = ""
        get() { return item.iconUrl}
    override var image: String = ""
        get() { return IMAGE_BASE_URL + item.iconUrl}
}