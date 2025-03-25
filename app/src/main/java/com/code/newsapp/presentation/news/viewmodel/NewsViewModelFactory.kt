package com.code.newsapp.presentation.news.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.code.newsapp.presentation.news.repository.NewsSearchRepository

class NewsViewModelFactory(private val context: Context,private val repository: NewsSearchRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchNewsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchNewsViewModel(context,repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}