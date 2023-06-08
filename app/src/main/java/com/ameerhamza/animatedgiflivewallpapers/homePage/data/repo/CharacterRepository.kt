package com.ameerhamza.animatedgiflivewallpapers.homePage.data.repo
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.ameerhamza.animatedgiflivewallpapers.BuildConfig
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.tvshowcharacters.DuckDuckGoCharactersApiService
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.tvshowcharacters.CharactersRemotePagingSource
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.tvshowcharacters.ShowCharacterServiceModule
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.tvshowcharacters.model.ShowCharacter
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.tvshowcharacters.model.ShowCharacters
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.tvshowcharacters.model.emptyCast
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject


class CharacterRepository @Inject constructor()  : MediaRepository{
    private var retrofit = ShowCharacterServiceModule.provideRetrofitForDuckDuckGoShow()
    private var apiInterface: DuckDuckGoCharactersApiService = retrofit.create(DuckDuckGoCharactersApiService::class.java)

    var showCharacters = ShowCharacters("Unspecified", emptyList())

    suspend fun getCast() = coroutineScope {
        launch {
            try {
                val response = apiInterface.getCast(BuildConfig.SHOW_API_URL)
                if (response.isSuccessful) {
                    var cast = response.body() ?: emptyCast
                    if (cast.RelatedTopics.isNotEmpty()) {
                        showCharacters = ShowCharacters("", cast.RelatedTopics.map {
                            val parts = it.FirstURL.split('/')
                            ShowCharacter(parts.last(), it.Text, it.Icon.URL)
                        })
                    }
                    Log.e(
                        "getCast",
                        "Fetched ${showCharacters.characters.size} characters from ${showCharacters.showName}"
                    )
                }
            } catch (Ex: Exception) {
                Log.e("Error", Ex.localizedMessage)
            }
        }
    }

    fun getCharactersWithPaging() = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100),
        pagingSourceFactory = { CharactersRemotePagingSource(apiInterface) })

    override fun fetchPagedItems() = Pager (
            config = PagingConfig(pageSize = 20, maxSize = 100),
            pagingSourceFactory = { CharactersRemotePagingSource(apiInterface) })
}
