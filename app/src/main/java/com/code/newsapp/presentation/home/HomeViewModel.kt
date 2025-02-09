package com.code.newsapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.code.newsapp.domain.usecases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val newsUseCase: NewsUseCases) : ViewModel() {

    val news = newsUseCase.getNews(sources = listOf("bbc-news", "abc-news", "al-jazeera-english"))
        .cachedIn(viewModelScope)
}