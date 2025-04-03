package com.code.newsapp.all.news.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.newsapp.all.news.model.response.NewsItem
import com.code.newsapp.all.news.model.response.NewsSearchApiResponse
import com.code.newsapp.all.news.repository.NewsSearchRepository
import com.code.newsapp.all.news.search.SearchNewsEvent
import com.code.newsapp.all.news.search.SearchNewsState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchNewsViewModel(
    val searchRepository: NewsSearchRepository,
) : ViewModel() {

    private val _state = mutableStateOf(SearchNewsState())
    val state: State<SearchNewsState> = _state

    var emptyMutableSearch = mutableStateOf(false)
    val emptySearchState: State<Boolean> = emptyMutableSearch

    private val _bookMarkData = MutableStateFlow<List<NewsItem?>>(emptyList())
    val bookMarkData: StateFlow<List<NewsItem?>> = _bookMarkData

    private val _newsItemBookMarkIcon = MutableStateFlow<List<NewsItem?>>(emptyList())
    val newsItemBookMarkIcon: StateFlow<List<NewsItem?>> = _newsItemBookMarkIcon

    private val _topItemData = MutableStateFlow<List<NewsItem?>>(emptyList())
    val topItemData: StateFlow<List<NewsItem?>> = _topItemData


    fun onEvent(event: SearchNewsEvent) {
        when (event) {
            is SearchNewsEvent.UpdateSearchQuery -> {
                Log.e("UpdateSearchQuery", "onEvent: ${_state.value}")
                if (event.searchQuery.isEmpty()) {
                    Log.e("UpdateSearchQuery", "Empty")
                    emptyMutableSearch.value = false
                } else {
                    Log.e("UpdateSearchQuery", "Not Empty")
                    _state.value = state.value.copy(searchQuery = event.searchQuery)
                    Log.e("UpdateSearchQuery", "Not Empty ${_state.value}")
                    emptyMutableSearch.value = true
                    getSearchNews(_state.value.searchQuery)
                }
            }

            is SearchNewsEvent.SearchNews -> {
                Log.e("UpdateSearchQuery", "SearchEvent.SearchNews")
                if (emptyMutableSearch.value == true) {
                    getSearchNews(_state.value.searchQuery)
                }
            }
        }
    }

    val newsSearchLiveData: LiveData<NewsSearchApiResponse>
        get() = searchRepository.searchNewsLiveData

    val topHeadNewsLiveData: LiveData<NewsSearchApiResponse>
        get() = searchRepository.topHeadNewsLiveData


    fun getSearchNews(searchNews: String) {
        viewModelScope.launch(Dispatchers.IO) {
            searchRepository.getSearchNews(searchNews)
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _topItemData.value = searchRepository.topItemData.value
        }
    }

    fun fetchTopHeadline() {
        viewModelScope.launch(Dispatchers.IO) {
            searchRepository.topHeadNews()
        }
    }
}