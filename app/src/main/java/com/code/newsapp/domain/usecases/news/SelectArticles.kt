package com.code.newsapp.domain.usecases.news

import com.code.newsapp.domain.model.Article
import com.code.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class SelectArticles (private val newsRepository: NewsRepository)  {

     operator fun invoke() : Flow<List<Article>>{
        return newsRepository.selectArticle()
    }
}