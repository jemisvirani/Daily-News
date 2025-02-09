package com.code.newsapp.presentation.news.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.code.newsapp.presentation.news.api.NewsSearchApi
import com.code.newsapp.presentation.news.model.response.Article
import com.code.newsapp.presentation.news.model.response.NewsSearchApiResponse
import com.code.newsapp.presentation.news.utils.Const
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NewsSearchRepository(val newsSearchApi: NewsSearchApi)  {

    private val searchNewsMutableLiveData = MutableLiveData<NewsSearchApiResponse>()
    private val topHeadNewsMutableLiveData = MutableLiveData<NewsSearchApiResponse>()

    val searchNewsLiveData : LiveData<NewsSearchApiResponse>
        get() = searchNewsMutableLiveData

    val topHeadNewsLiveData : LiveData<NewsSearchApiResponse>
        get() = topHeadNewsMutableLiveData

    private val _topItemData = MutableStateFlow<List<Article?>>(emptyList())
    val topItemData : StateFlow<List<Article?>> = _topItemData

    suspend fun getSearchNews(searchNews : String){
        val response = newsSearchApi.searchNews(searchNews,Const.API_KEY)
        if (response.isSuccessful){
            searchNewsMutableLiveData.postValue(response.body())
            Log.e("SearchNews", response.body().toString())
        }else{
            Log.e("ErrorSearchNews", response.message())
        }
    }

    suspend fun topHeadNews(){
        val response = newsSearchApi.newsTopHead(Const.COUNTRY,Const.API_KEY)
        if (response.isSuccessful){
            topHeadNewsMutableLiveData.postValue(response.body())
            _topItemData.value = response.body()?.articles ?: emptyList()
            Log.e("TopHeadNews", response.body().toString())
        }else{
            Log.e("ErrorTopHeadNews", response.message())
        }
    }
}