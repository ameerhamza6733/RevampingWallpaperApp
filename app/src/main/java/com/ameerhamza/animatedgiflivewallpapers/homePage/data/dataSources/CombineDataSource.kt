package com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ameerhamza.animatedgiflivewallpapers.BuildConfig
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.*
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.servies.VideoWallpaperService
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements


class CombineDataSource(
    private val videoApiService: VideoWallpaperService,
    private val wallpaperRequest: WallpaperRequest
) :
    PagingSource<DiscoverWallpaperPagingKey, WallpaperResponse>() {
    override fun getRefreshKey(state: PagingState<DiscoverWallpaperPagingKey, WallpaperResponse>): DiscoverWallpaperPagingKey? {
        return null
    }

    override suspend fun load(params: LoadParams<DiscoverWallpaperPagingKey>): LoadResult<DiscoverWallpaperPagingKey, WallpaperResponse> {
        return try {
            val pagingKey =
                params.key ?: DiscoverWallpaperPagingKey(1, 1)

            val videoWallpaper = try {
                videoApiService.getVideos(
                    perPage = 10,
                    query = wallpaperRequest.searchTerm,
                    page = pagingKey.videoPageKey ?: 1,
                    orientation = "portrait"
                )
            } catch (e: Exception) {
                null
            }

            val imageWallpaper = try {
                withContext(Dispatchers.IO) {
                    getImageWallpaper(
                        wallpaperRequest.searchTerm,
                        pagingKey.imageWallpaperKey ?: 1
                    )
                }
            } catch (e: Exception) {
                null
            }

            val wallpapers = try {
                withContext(Dispatchers.Default) {
                    Log.d(TAG,"marge image and video wallpaper total video wallpapers ${videoWallpaper?.videos?.size} image wallpaper ${imageWallpaper?.imageWallpaperListResponse?.size}")
                    margeAllWallpaper(
                        videoWallpaper?.videos,
                        imageWallpaper?.imageWallpaperListResponse
                    )
                }
            } catch (e: Exception) {
                null
            }

            val videoWallpaperNextPage =
                if (videoWallpaper?.nextPage != null) pagingKey.videoPageKey!! + 1 else null
            val imageWallpaperNextPage =
                if (imageWallpaper?.nextPageNumber != null) pagingKey.imageWallpaperKey!! + 1 else null
            val discoverWallpaperDataSource = DiscoverWallpaperPagingKey(
                videoWallpaperNextPage,
                imageWallpaperKey = imageWallpaperNextPage
            )

            Log.d(TAG,"wallpaper size ${wallpapers?.size}")

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


    private fun margeAllWallpaper(
        videoWallpaperList: List<VideoWallpaperPixelsApiResponse.VideoWallpaperPixelsVideoListResponse>?,
        imageWallpaperList: List<ImageWallpaperListApiResponse>?
    ): MutableList<WallpaperResponse> {
        val wallpaperResponse = mutableListOf<WallpaperResponse>()

        videoWallpaperList?.map { videoWallpaperPixelsVideoListResponse ->
            WallpaperResponse(
                image = videoWallpaperPixelsVideoListResponse.image,
                id = System.currentTimeMillis(),
                url = videoWallpaperPixelsVideoListResponse.url,
                wallpaperType = WallpaperType.VIDEO
            )
        }?.let {
            wallpaperResponse.addAll(it)
        }

        imageWallpaperList?.map { imageWallpaperListApiResponse ->
            WallpaperResponse(
                image = imageWallpaperListApiResponse.lowResImageUrl,
                id = System.currentTimeMillis(),
                url = imageWallpaperListApiResponse.imageUrl,
                wallpaperType = WallpaperType.IMAGE
            )
        }?.let {
            wallpaperResponse.addAll(it)
        }

        return wallpaperResponse
    }

    private fun getImageWallpaper(search: String, page: Int): ImageWallpaperApiResponse {
        return try {
            val photoList = arrayListOf<ImageWallpaperListApiResponse>()
            val doc: Document =
                Jsoup.connect((BuildConfig.IMAGE_BASE_URL_SHOPIFY).plus("page=$page&q=$search"))
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .get()
            val eImageUrl: Elements = doc.select("img")
            val eTitle: Elements = doc.select("p.photo-tile__title")
            val eTotelPages: Elements = doc.select("li.next")
            val eCategories: Elements = doc.select("div[class=scrollable-carousel__item]")

            val itr: ListIterator<Element> = eTitle.listIterator()
            val iteratorImageUrl: ListIterator<Element> = eImageUrl.listIterator()


            val nextPage = try {
                val href = eTotelPages.get(0).selectFirst("a[rel=next]").attr("href")
                val pageNumber = href.substring(href.indexOf("page=") + 5, href.indexOf("&q="))
                pageNumber.toInt()
            } catch (e: Exception) {
                null
            }
            val categories: MutableList<ImageWallpaperSimilarCategories> = ArrayList<ImageWallpaperSimilarCategories>()

            for (element in eCategories) {
                val title: String = element.select("h4").text()
                if (ImageWallpaperSimilarCategories.blackListCategories.containsKey(
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
                categories.add(ImageWallpaperSimilarCategories(title))
            }


            while (itr.hasNext()) {
                val cell: Element = itr.next()
                val title: String = cell.ownText()
                var imageUrl: String = iteratorImageUrl.next().attr("data-srcset")
                val lowResImageUrl = imageUrl.split(" ".toRegex()).toTypedArray()[0]
                imageUrl = imageUrl.substring(0, imageUrl.indexOf("?"))
                photoList.add(ImageWallpaperListApiResponse(lowResImageUrl, imageUrl, title))
            }
            ImageWallpaperApiResponse(
                nextPageNumber = nextPage,
                imageWallpaperListResponse = photoList
            )
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }


    }


    companion object {
        private val TAG = "DiscoverWallpaerTAG"
    }

}

data class DiscoverWallpaperPagingKey(val videoPageKey: Int?, val imageWallpaperKey: Int?)

