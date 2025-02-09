package com.code.newsapp.presentation.news.search

import androidx.paging.PagingData
import com.code.newsapp.presentation.news.model.response.Article
import kotlinx.coroutines.flow.Flow

data class SearchNewsState(
    val searchQuery: String = "",
    val articles: Flow<PagingData<Article>>? = null
)