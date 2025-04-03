package com.code.newsapp.all.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import com.code.newsapp.all.checkinternet.interfaces.ConnectivityObserver
import com.code.newsapp.all.drawer.DrawerScreen
import com.code.newsapp.all.news.model.response.NewsItem
import com.code.newsapp.all.news.viewmodel.SearchNewsViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navigateToDetails: (NewsItem?) -> Unit,
    searchNewsViewModel: SearchNewsViewModel,
    status: State<ConnectivityObserver.Status>,
    infoDialog: MutableState<Boolean>,
) {
    DrawerScreen(
        navigateToDetails = navigateToDetails,
        searchNewsViewModel = searchNewsViewModel,
        status,
        infoDialog
    )
}

