package com.code.newsapp.all.news.search

sealed class SearchNewsEvent {
    data class UpdateSearchQuery(val searchQuery: String) : SearchNewsEvent()

    object SearchNews : SearchNewsEvent()
}