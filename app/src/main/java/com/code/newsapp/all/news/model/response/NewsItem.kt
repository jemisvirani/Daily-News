package com.code.newsapp.all.news.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsItem(
    @field:SerializedName("author")
    val author: String? = null,
    @field:SerializedName("content")
    val content: String? = null,
    @field:SerializedName("description")
    val description: String? = null,
    @field:SerializedName("publishedAt")
    val publishedAt: String? = null,
    @field:SerializedName("source")
    val source: Source? = null,
    @field:SerializedName("title")
    val title: String? = null,
    @field:SerializedName("url")
    val url: String? = null,
    @field:SerializedName("urlToImage")
    val urlToImage: String? = null,
) : Parcelable {
    constructor() : this(null, null, null, null, null, null, null, null)
}