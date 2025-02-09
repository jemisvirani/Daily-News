package com.code.newsapp.data.remote.dto

import com.code.newsapp.domain.model.Article
import com.google.gson.annotations.SerializedName

data class NewsResponse(

	@field:SerializedName("totalResults")
	val totalResults: Int,

	@field:SerializedName("articles")
	val articles: List<Article>,

	@field:SerializedName("status")
	val status: String
)