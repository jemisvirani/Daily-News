package com.code.newsapp.presentation.news.search

sealed class SearchNewsEvent {
    data class UpdateSearchQuery(val searchQuery : String) : SearchNewsEvent()

    object SearchNews : SearchNewsEvent()
}