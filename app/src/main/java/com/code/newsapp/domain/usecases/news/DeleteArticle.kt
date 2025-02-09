package com.code.newsapp.domain.usecases.news

import com.code.newsapp.domain.model.Article
import com.code.newsapp.domain.repository.NewsRepository

class DeleteArticle (private val newsRepository: NewsRepository)  {

    suspend operator fun invoke(article: Article){
        newsRepository.deleteArticle(article)
    }
}