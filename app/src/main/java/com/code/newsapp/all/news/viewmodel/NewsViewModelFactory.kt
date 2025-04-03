package com.code.newsapp.all.news.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.code.newsapp.all.news.repository.NewsSearchRepository

class NewsViewModelFactory(
    private val repository: NewsSearchRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchNewsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchNewsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}