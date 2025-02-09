package com.code.newsapp.presentation.news.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.code.newsapp.presentation.news.repository.NewsSearchRepository

class SearchNewsViewModelFactory(val searchRepository: NewsSearchRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchNewsViewModel(searchRepository) as T
    }
}