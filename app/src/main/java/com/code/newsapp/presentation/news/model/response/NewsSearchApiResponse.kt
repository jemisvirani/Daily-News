package com.code.newsapp.presentation.news.model.response

data class NewsSearchApiResponse(
    val articles: List<Article?>,
    val status: String,
    val totalResults: Int
)