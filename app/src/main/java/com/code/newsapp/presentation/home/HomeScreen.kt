package com.code.newsapp.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.code.newsapp.presentation.drawer.DrawerScreen
import com.code.newsapp.presentation.news.model.response.Article
import com.code.newsapp.presentation.news.viewmodel.SearchNewsViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navigateToDetails: (Article?) -> Unit,
    searchNewsViewModel: SearchNewsViewModel,
    navController: NavHostController,
    ) {
    DrawerScreen(navigateToDetails = navigateToDetails, searchNewsViewModel = searchNewsViewModel,navController)
}

