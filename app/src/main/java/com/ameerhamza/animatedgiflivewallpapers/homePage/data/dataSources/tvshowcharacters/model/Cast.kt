package com.ameerhamza.animatedgiflivewallpapers.homePage.data.dataSources.tvshowcharacters.model

data class Cast(
    val AbstractSource: String,
    val AbstractURL: String,
    val Heading: String,
    val RelatedTopics: List<Topic>
)

val emptyCast = Cast("", "", "No show specified", emptyList())
