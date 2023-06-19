package com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources

import android.util.Log
import com.ameerhamza.animatedgiflivewallpapers.BuildConfig
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.tvshowcharacters.CharacterDataProvider
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.tvshowcharacters.model.ShowCharacter
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.tvshowcharacters.model.emptyCast
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.repo.CharacterRepository
import javax.inject.Inject


class PhotoMediaProvider(
    private val query: String,
) : MediaProvider {

    var charactersRepository = CharacterRepository()

    override suspend fun load(
        query: String,
        page: Int,
        itemsPerPage: Int
    ): List<MediaDataProvider>{
        lateinit var showCharacters: List<MediaDataProvider>
        return try {
            Log.d("Paging", "Calling getCast()")
            val response = charactersRepository.apiInterface.getCast(BuildConfig.SHOW_API_URL)
            if (response.isSuccessful) {
                var cast = response.body() ?: emptyCast
                if (cast.RelatedTopics.isNotEmpty()) {

                    showCharacters = cast.RelatedTopics.filter{
                        it.Icon.URL.isNotEmpty()
                    }.map {
                        val parts = it.FirstURL.split('/')
                        CharacterDataProvider(ShowCharacter(parts.last(), it.Text, it.Icon.URL))
                    }
                    Log.d("Paging", "${showCharacters.size} Simpson Characters used out of ${cast.RelatedTopics.size}")
                }
                Log.e(
                    "Paging",
                    "Fetched ${showCharacters.size} characters from cast of Simpsons"
                )
            }
            return showCharacters
        } catch (Ex: Exception) {
            Log.e("Error", Ex.localizedMessage)
            emptyList()
        }
    }
}

