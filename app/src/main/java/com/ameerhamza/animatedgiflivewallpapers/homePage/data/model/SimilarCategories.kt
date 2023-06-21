package com.ameerhamza.animatedgiflivewallpapers.homePage.data.model

data class SimilarCategories(val name: String) {
    companion object {
        val blackListCategores = HashMap<String, String>().apply {
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