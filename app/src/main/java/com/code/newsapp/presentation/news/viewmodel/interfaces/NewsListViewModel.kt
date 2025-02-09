package com.code.newsapp.presentation.news.viewmodel.interfaces

import androidx.lifecycle.LiveData
import com.code.newsapp.presentation.news.model.response.NewsSearchApiResponse

interface NewsListViewModel {
    val news : LiveData<NewsSearchApiResponse>

    fun getSearchNews()
}