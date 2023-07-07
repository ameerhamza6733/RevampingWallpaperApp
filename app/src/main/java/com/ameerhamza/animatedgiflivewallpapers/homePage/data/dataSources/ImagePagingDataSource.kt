package com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ameerhamza.animatedgiflivewallpapers.BuildConfig
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.ImageWallpaperListApiResponse
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.ImageWallpaperSimilarCategories
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements


class ImagePagingDataSource : PagingSource<Int, ImageWallpaperListApiResponse>() {


    override fun getRefreshKey(state: PagingState<Int, ImageWallpaperListApiResponse>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageWallpaperListApiResponse> {
        try {

            val photoList = arrayListOf<ImageWallpaperListApiResponse>()
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

            val categories: MutableList<ImageWallpaperSimilarCategories> = ArrayList<ImageWallpaperSimilarCategories>()
            try {
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
                    println("link $link")
                    categories.add(ImageWallpaperSimilarCategories(title))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            while (itr.hasNext()) {
                val cell: Element = itr.next()
                val title: String = cell.ownText()
                var imageUrl: String = iteratorImageUrl.next().attr("data-srcset")
                val lowResImageUrl = imageUrl.split(" ".toRegex()).toTypedArray()[0]
                imageUrl = imageUrl.substring(0, imageUrl.indexOf("?"))
                photoList.add(ImageWallpaperListApiResponse(lowResImageUrl, imageUrl, title))
            }

            return LoadResult.Page(
                data = photoList,
                nextKey = if (nextPage != null) nextPage + 1 else null,
                prevKey = if (nextPage != null && nextPage > 1) nextPage - 1 else null
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}