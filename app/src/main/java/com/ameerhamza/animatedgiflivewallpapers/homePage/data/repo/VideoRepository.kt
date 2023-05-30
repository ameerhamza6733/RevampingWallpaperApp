package com.ameerhamza.animatedgiflivewallpapers.homePage.data.repo
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.ameerhamza.animatedgiflivewallpapers.comman.data.Result
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.VideoRemoteFakeDataSource
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.VideoRemotePagingSource
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.VideoWallpaperUi
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.servies.VideoWallpaperService
import javax.inject.Inject

class VideoRepository @Inject constructor(private val fakeVideoDataSource:VideoRemoteFakeDataSource, private val videoApiService: VideoWallpaperService) {

    suspend fun getVideos(): Result<List<VideoWallpaperUi>?> {
        return try {
            val response = fakeVideoDataSource.getVideos()
                val videos = response?.map { video ->
                    VideoWallpaperUi(
                        thumbnail = video.image
                    , duration = 0,
                    videoUrl = video.url)
                }
             Result.Success(videos)

        }catch (e: Exception) {
            Result.Error(e.message ?: "An unknown error occurred")
        }
    }


     fun getVideosWithPaging() = Pager(config = PagingConfig(pageSize = 20, maxSize = 100), pagingSourceFactory ={VideoRemotePagingSource(videoApiService)} )

}
