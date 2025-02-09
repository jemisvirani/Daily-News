package com.code.newsapp.presentation.onboarding

import androidx.annotation.DrawableRes
import com.code.newsapp.R

data class Page(
    val tittle: String,
    val description: String,
    @DrawableRes val image: Int
)

val pages = listOf(
    Page(
        tittle = "Stay Connected Everywhere, Anytime",
        "Welcome to DailyNews,your ultimate destination for breaking news, exclusive stories, and tailored content",
        image = R.drawable.onboarding1
    ),Page(
        tittle = "Become a Savvy Global Citizen.",
        "Discover tailored news that aligns with your interest and preferences. Your personalized news journey awaits!",
        image = R.drawable.onboarding2
    ),Page(
        tittle = "Enhance your News journey Now!",
        "Be part of our dynamic community and contribute your insights and participate in enriching conversations.",
        image = R.drawable.onboarding3
    )
)