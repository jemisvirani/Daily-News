package com.code.newsapp.presentation.bookmark

import com.code.newsapp.domain.model.Article

data class BookmarkState(val articles : List<Article> = emptyList())