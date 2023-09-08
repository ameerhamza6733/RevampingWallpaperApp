package com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ameerhamza.animatedgiflivewallpapers.BuildConfig
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.ImageWallpaper
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.ImageWallpaperApiResponse
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.SimilarCategories
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.VideoWallpaperPixelsApiResponse
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.WallpaperResponse
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.servies.VideoWallpaperService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

data class DiscoverWallpaperPagingKey(val videoPageKey: Int?, val imageWallpaperKey: Int?)

class DiscoverWallpaperDataSource(
    private val videoApiService: VideoWallpaperService,
    private val coroutineScope: CoroutineScope
) :
    PagingSource<DiscoverWallpaperPagingKey, WallpaperResponse>() {

    override fun getRefreshKey(state: PagingState<DiscoverWallpaperPagingKey, WallpaperResponse>): DiscoverWallpaperPagingKey? {
        return null
    }

    override suspend fun load(params: LoadParams<DiscoverWallpaperPagingKey>): LoadResult<DiscoverWallpaperPagingKey, WallpaperResponse> {
        return try {

            val pagingKey =
                params.key ?: DiscoverWallpaperPagingKey(videoPageKey = 1, imageWallpaperKey = 1)

            val videoWallpaper = try {
                videoApiService.getVideos(
                    perPage = params.loadSize,
                    query = "car",
                    page = pagingKey.videoPageKey ?: 1,
                    orientation = "portrait"
                )
            } catch (e: Exception) {
                null
            }

            val imageWallpaper = try {
                withContext(Dispatchers.IO) { getImageWallpaper() }
            } catch (e: Exception) {
                null
            }
            Log.d(TAG, "total image wallpaper ${imageWallpaper?.images?.size}")
            val wallpapers = try {
                withContext(Dispatchers.Default) {
                    margeAllWallpaper(
                        null,
                        imageWallpaper?.images
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }

            val discoverWallpaperDataSource =
                margeKey(videoWallpaper?.nextPage, imageWallpaper?.nextPage)


            LoadResult.Page(
                data = wallpapers ?: listOf(),
                prevKey = null,
                nextKey = discoverWallpaperDataSource
            )


        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }

    private fun margeKey(
        videoWallpaperNextPage: String?,
        imageWallpaperNExtPage: Int?
    ): DiscoverWallpaperPagingKey {
        return DiscoverWallpaperPagingKey(
            videoPageKey = videoWallpaperNextPage?.toIntOrNull(),
            imageWallpaperKey = imageWallpaperNExtPage
        )
    }

    private fun margeAllWallpaper(
        videoWallpaperList: List<VideoWallpaperPixelsApiResponse.VideoWallpaperPixelsVideoListResponse>?,
        imageWallpaperList: List<ImageWallpaper>?
    ): MutableList<WallpaperResponse> {
        val wallpaperResponse = mutableListOf<WallpaperResponse>()

        videoWallpaperList?.map { videoWallpaperPixelsVideoListResponse ->
            WallpaperResponse(
                image = videoWallpaperPixelsVideoListResponse.image,
                id = System.currentTimeMillis(),
                url = videoWallpaperPixelsVideoListResponse.url,
                wallpaperType = WallpaperResponse.VIDEO_WALLPAPER
            )
        }?.let {
            wallpaperResponse.addAll(it)
        }

        imageWallpaperList?.map { imageWallpaperListApiResponse ->
            WallpaperResponse(
                image = imageWallpaperListApiResponse.lowResImageUrl,
                id = System.currentTimeMillis(),
                url = imageWallpaperListApiResponse.imageUrl,
                wallpaperType = WallpaperResponse.IMAGE_WALLPAPER
            )
        }?.let {
            wallpaperResponse.addAll(it)
        }

        return wallpaperResponse
    }

    private fun getImageWallpaper(): ImageWallpaperApiResponse {
        return try {
            val photoList = arrayListOf<ImageWallpaper>()
            val doc: Document = Jsoup.connect(BuildConfig.IMAGE_BASE_URL_SHOPIFY)
                .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                .get()

            val eImageUrl: Elements = doc.select("img")
            val eTitle: Elements = doc.select("p.photo-tile__title")
            val eTotelPages: Elements = doc.select("span.next>a")
            val eCategories: Elements = doc.select("div[class=scrollable-carousel__item]")

            val itr: ListIterator<Element> = eTitle.listIterator()
            val iteratorImageUrl: ListIterator<Element> = eImageUrl.listIterator()

            val nextPage = if (eTotelPages.size > 0) {
                eTotelPages.get(0).attr("href").toInt()
            } else {
                null
            }

            val categories: MutableList<SimilarCategories> = ArrayList<SimilarCategories>()

            for (element in eCategories) {
                val title: String = element.select("h4").text()
                if (SimilarCategories.blackListCategores.containsKey(
                        title.lowercase(

                        )
                    )
                ) continue
                var backgroundImage: String = element.select("div").attr("style")
                val link: String = element.select("a").attr("href")
                backgroundImage = backgroundImage.substring(
                    backgroundImage.indexOf("(") + 1,
                    backgroundImage.indexOf(")")
                )
                println("link $link")
                categories.add(SimilarCategories(title))
            }


            while (itr.hasNext()) {
                val cell: Element = itr.next()
                val title: String = cell.ownText()
                var imageUrl: String = iteratorImageUrl.next().attr("data-srcset")
                val lowResImageUrl = imageUrl.split(" ".toRegex()).toTypedArray()[0]
                imageUrl = imageUrl.substring(0, imageUrl.indexOf("?"))
                photoList.add(ImageWallpaper(lowResImageUrl, imageUrl, title))
            }
            ImageWallpaperApiResponse(
                nextPage = nextPage,
                images = photoList
            )
        } catch (e: Exception) {
            throw e
        }


    }


    companion object {
        private val TAG = "DiscoverWallpaerTAG"
    }

}

