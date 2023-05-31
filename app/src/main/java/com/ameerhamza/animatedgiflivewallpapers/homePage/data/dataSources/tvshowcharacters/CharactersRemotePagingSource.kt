package com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.tvshowcharacters

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ameerhamza.animatedgiflivewallpapers.BuildConfig
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.tvshowcharacters.model.ShowCharacter
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.tvshowcharacters.model.emptyCast

class CharactersRemotePagingSource : PagingSource<String, CharacterDataProvider>() {
    var retrofit = RetrofitClient.getInstance()
    var apiInterface = retrofit.create(ApiInterface::class.java)

    var showCharacters: List<CharacterDataProvider> = emptyList()

    override suspend fun load(
        params: LoadParams<String>
    ): LoadResult<String, CharacterDataProvider> {
        return try {
            val position = (params.key ?: "1")
            val response = apiInterface.getCast(BuildConfig.SHOW_API_URL)
            if (response.isSuccessful) {
                var cast = response.body() ?: emptyCast
                if (cast.RelatedTopics.isNotEmpty()) {
                    showCharacters = cast.RelatedTopics.map {
                        val parts = it.FirstURL.split('/')
                        CharacterDataProvider(ShowCharacter(parts.last(), it.Text, it.Icon.URL))
                    }
                }
                Log.e(
                    "CharPagingSource.load",
                    "Fetched ${showCharacters.size} characters from cast of Simpsons"
                )
            }
            return LoadResult.Page(
                data = showCharacters,
                prevKey = if (position=="1")  null else (position.toInt()-1).toString(),
// We are fetching all Simpson characters at once
//                nextKey = if (position.toInt()==response.totalResults) null else (position.toInt()-1).toString()
                nextKey = null
            )

        } catch (e: Exception) {
            Log.e("Error", e.localizedMessage)
            LoadResult.Error(e)
            // Handle errors in this block and return LoadResult.Error if it is an
            // expected error (such as a network failure).
        }
    }

    override fun getRefreshKey(state: PagingState<String, CharacterDataProvider>): String? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.toInt()?.plus(1)?.toString() ?: anchorPage?.nextKey?.toInt()?.minus(1).toString()
        }
    }
}