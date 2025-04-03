package com.code.newsapp.all.news.repository

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.code.newsapp.all.checkinternet.interfaces.ConnectivityObserver
import com.code.newsapp.all.news.api.NewsSearchApi
import com.code.newsapp.all.news.model.response.NewsItem
import com.code.newsapp.all.news.model.response.NewsSearchApiResponse
import com.code.newsapp.all.news.utils.Const
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class NewsSearchRepository(
    private val newsSearchApi: NewsSearchApi,
    private val status: State<ConnectivityObserver.Status>,
    private val infoDialog: MutableState<Boolean>,
) {

    private val searchNewsMutableLiveData = MutableLiveData<NewsSearchApiResponse>()
    private val topHeadNewsMutableLiveData = MutableLiveData<NewsSearchApiResponse>()

    val searchNewsLiveData: LiveData<NewsSearchApiResponse>
        get() = searchNewsMutableLiveData

    val topHeadNewsLiveData: LiveData<NewsSearchApiResponse>
        get() = topHeadNewsMutableLiveData

    private val _topItemData = MutableStateFlow<List<NewsItem?>>(emptyList())
    val topItemData: StateFlow<List<NewsItem?>> = _topItemData

    suspend fun getSearchNews(searchNews: String) {

        if (!infoDialog.value) {
            try {
                val response = newsSearchApi.searchNews(searchNews, Const.API_KEY)
                if (response.isSuccessful) {
                    searchNewsMutableLiveData.postValue(response.body())
                    Log.e("SearchNews", response.body().toString())
                } else {
                    Log.e("ErrorSearchNews", response.message())
                }
            } catch (e: Exception) {
                Log.e("ErrorData", "App crashed: ${e.localizedMessage}")
            }
        }


    }

    suspend fun topHeadNews() {

        when (status.value) {
            ConnectivityObserver.Status.Available -> infoDialog.value = false
            ConnectivityObserver.Status.Unavailable -> infoDialog.value = true
            ConnectivityObserver.Status.Losing -> infoDialog.value = false
            ConnectivityObserver.Status.Lost -> infoDialog.value = true
            else -> infoDialog.value = false
        }

        if (!infoDialog.value) {
            try {
                val response = newsSearchApi.newsTopHead(Const.COUNTRY, Const.API_KEY)
                if (response.isSuccessful) {
                    topHeadNewsMutableLiveData.postValue(response.body())
                    _topItemData.value = response.body()!!.articles
                    Log.d("TopHeadNews", response.body().toString())
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("ErrorTopHeadNews", "Error: ${response.message()}, Body: $errorBody")
                }
            } catch (e: Exception) {
                Log.e("ErrorData", "App crashed: ${e.localizedMessage}")
            }
        }
    }
}



