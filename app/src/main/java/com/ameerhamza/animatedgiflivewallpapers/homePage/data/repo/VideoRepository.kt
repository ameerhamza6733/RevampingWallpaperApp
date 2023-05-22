package com.ameerhamza.animatedgiflivewallpapers.homePage.data.repo
import com.ameerhamza.animatedgiflivewallpapers.comman.data.Result
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.VideoRemoteDataSource
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.VideoRemoteFakeDataSource
import com.ameerhamza.animatedgiflivewallpapers.homePage.data.model.VideoWallpaperUi
import javax.inject.Inject

class VideoRepository @Inject constructor(private val fakeVideoDataSouce:VideoRemoteFakeDataSource) {

    suspend fun getVideos(): Result<List<VideoWallpaperUi>?> {
        return try {
            val response = fakeVideoDataSouce.getVideos()
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


}
