package com.code.newsapp.presentation.news.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val baseUrl = "https://newsapi.org/"

    fun getInstance(): NewsSearchApi {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(NewsSearchApi::class.java)
    }
}





