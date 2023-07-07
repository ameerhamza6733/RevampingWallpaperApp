package com.ameerhamza.animatedgiflivewallpapers.homePage.data.model

data class ImageWallpaperApiResponse(val nextPageNumber:Int?,val imageWallpaperListResponse:List<ImageWallpaperListApiResponse>)
data class ImageWallpaperListApiResponse(val lowResImageUrl: String, val imageUrl: String, val title: String)
data class ImageWallpaperSimilarCategories(val name: String) {
    companion object {
        val blackListCategories = HashMap<String, String>().apply {
            put("women", "women")
            put("sex", "sex");
            put("man", "man");
            put("girls", "girls");
            put("girl", "girl");
            put("womens", "womens");
            put("swimming", "swimming");
            put("bikini", "bikini");
            put("bra", "bra");
        }

    }
}