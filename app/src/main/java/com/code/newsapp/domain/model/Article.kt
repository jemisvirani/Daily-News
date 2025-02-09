package com.code.newsapp.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity()
data class Article(

	@field:SerializedName("author")
	val author: String?,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("content")
	val content: String,

	@field:SerializedName("publishedAt")
	val publishedAt: String,

	@field:SerializedName("source")
	val source: Source,

	@field:SerializedName("url")
	@PrimaryKey val url: String,

	@field:SerializedName("urlToImage")
	val urlToImage: String
) : Parcelable