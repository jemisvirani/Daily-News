package com.code.newsapp.all.news.api

import com.code.newsapp.all.news.model.response.NewsSearchApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsSearchApi {

    @GET("v2/everything")
    suspend fun searchNews(
        @Query("q") q: String,
        @Query("apiKey") apiKey: String,
    ): Response<NewsSearchApiResponse>

    @GET("v2/top-headlines")
    suspend fun newsTopHead(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String,
    ): Response<NewsSearchApiResponse>

}