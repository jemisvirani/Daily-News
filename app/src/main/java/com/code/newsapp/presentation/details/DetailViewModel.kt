package com.code.newsapp.presentation.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.newsapp.domain.model.Article
import com.code.newsapp.domain.usecases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val newsUseCases: NewsUseCases) : ViewModel() {

    var sideEffect = mutableStateOf<String?>(null)
        private set

    fun onEvent(event : DetailsEvent){
        when(event){
            is DetailsEvent.UpsertDeleteArticle -> {
                viewModelScope.launch {
                    val article = newsUseCases.selectArticle(event.article.url)
                    if (article == null){
                        upsertArticle(event.article)
                    }else{
                        deleteArticle(event.article)
                    }
                }
            }
            is DetailsEvent.RemoveSideEffect ->{
                sideEffect.value = null
            }
        }
    }

    private suspend fun deleteArticle(article: Article) {
        newsUseCases.deleteArticle(article = article)
        sideEffect.value = "Article Deleted"
    }

    private suspend fun upsertArticle(article: Article) {
        newsUseCases.upsertArticle(article = article)
        sideEffect.value = "Article Saved"
    }
}