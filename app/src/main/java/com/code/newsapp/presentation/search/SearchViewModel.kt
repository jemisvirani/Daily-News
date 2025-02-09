package com.code.newsapp.presentation.search

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.code.newsapp.domain.usecases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
) : ViewModel() {

    private val _state = mutableStateOf(SearchState())
    val state: State<SearchState> = _state

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.UpdateSearchQuery -> {
                Log.e("UpdateSearchQuery", "onEvent: ${_state.value}")
                if (event.searchQuery.isEmpty()){
                    Log.e("UpdateSearchQuery", "Empty")
                }else{
                    Log.e("UpdateSearchQuery", "Not Empty")
                    _state.value = state.value.copy(searchQuery = event.searchQuery)
                }
            }

            is SearchEvent.SearchNews -> {
                searchNews()
                Log.e("UpdateSearchQuery", "SearchEvent.SearchNews")
            }

            else -> {}
        }
    }

    private fun searchNews() {
        val articles = newsUseCases.searchNews(
            searchQuery = state.value.searchQuery,
            sources = listOf("bbc-news", "abc-news", "al-jazeera-english")
        ).cachedIn(viewModelScope)
        _state.value = state.value.copy(articles = articles)
    }
}