package com.code.newsapp.all.news.model.response

data class NewsSearchApiResponse(
    val articles: List<NewsItem?>,
    val status: String,
    val totalResults: Int,
)