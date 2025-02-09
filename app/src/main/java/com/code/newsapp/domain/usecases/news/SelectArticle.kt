package com.code.newsapp.domain.usecases.news

import com.code.newsapp.domain.model.Article
import com.code.newsapp.domain.repository.NewsRepository

class SelectArticle (private val newsRepository: NewsRepository)  {

    suspend operator fun invoke(url: String) : Article?{
       return newsRepository.selectArticle(url)
    }
}