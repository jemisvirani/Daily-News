package com.code.newsapp.presentation.news.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.newsapp.presentation.firebase.BookMarkData.fetchBookMarkNews
import com.code.newsapp.presentation.news.model.response.Article
import com.code.newsapp.presentation.news.model.response.NewsSearchApiResponse
import com.code.newsapp.presentation.news.repository.NewsSearchRepository
import com.code.newsapp.presentation.news.search.SearchNewsEvent
import com.code.newsapp.presentation.news.search.SearchNewsState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchNewsViewModel(val searchRepository: NewsSearchRepository) : ViewModel(){

    private val _state = mutableStateOf(SearchNewsState())
    val state: State<SearchNewsState> = _state

     var emptyMutableSearch = mutableStateOf(false)
    val emptySearchState : State<Boolean> = emptyMutableSearch

    private val _articles = MutableLiveData<List<Article?>>(emptyList())
    val articles : LiveData<List<Article?>> = _articles

    private val _articlesBookMarkIcon = MutableStateFlow<List<Article?>>(emptyList())
    val articlesBookMarkIcon : StateFlow<List<Article?>> = _articlesBookMarkIcon

    private val _topItemData = MutableStateFlow<List<Article?>>(emptyList())
    val topItemData : StateFlow<List<Article?>> = _topItemData

    fun onEvent(event : SearchNewsEvent){
        when(event){
            is SearchNewsEvent.UpdateSearchQuery ->{
                Log.e("UpdateSearchQuery", "onEvent: ${_state.value}")
                if (event.searchQuery.isEmpty()){
                    Log.e("UpdateSearchQuery", "Empty")
                    emptyMutableSearch.value = false
                }else{
                    Log.e("UpdateSearchQuery", "Not Empty")
                    _state.value = state.value.copy(searchQuery = event.searchQuery)
                    Log.e("UpdateSearchQuery", "Not Empty ${_state.value}")
                    emptyMutableSearch.value = true
                    getSearchNews(_state.value.searchQuery)
                }
            }

            is SearchNewsEvent.SearchNews -> {
                Log.e("UpdateSearchQuery", "SearchEvent.SearchNews")
                if (emptyMutableSearch.value == true){
                    getSearchNews(_state.value.searchQuery)
                }
            }
        }
    }

     val newsSearchLiveData : LiveData<NewsSearchApiResponse>
        get() = searchRepository.searchNewsLiveData

    val topHeadNewsLiveData : LiveData<NewsSearchApiResponse>
        get() = searchRepository.topHeadNewsLiveData


    fun getSearchNews(searchNews : String){
         viewModelScope.launch(Dispatchers.IO){
             searchRepository.getSearchNews(searchNews)
         }
    }

    init {
        viewModelScope.launch(Dispatchers.IO){
            _topItemData.value = searchRepository.topItemData.value
                fetchBookMarkNews().collect{ itList ->
                    _articles.postValue(itList)
                    _articlesBookMarkIcon.value = itList
                    Log.e("BookData",itList.toString())
            }
        }
    }

    fun fetchTopHeadline(){
        viewModelScope.launch(Dispatchers.IO){
            searchRepository.topHeadNews()
        }
    }

}