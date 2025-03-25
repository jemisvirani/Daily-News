package com.code.newsapp.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.code.newsapp.checkinternet.interfaces.ConnectivityObserver
import com.code.newsapp.presentation.common.ArticleCard
import com.code.newsapp.presentation.dilaog.InfoDialog
import com.code.newsapp.presentation.news.model.response.Article
import com.code.newsapp.presentation.news.search.SearchNewsEvent
import com.code.newsapp.presentation.news.searchscreen.SearchBar
import com.code.newsapp.presentation.news.viewmodel.SearchNewsViewModel
import com.code.newsapp.presentation.shimmer.ShimmerListItem
import kotlinx.coroutines.delay
import network.chaintech.sdpcomposemultiplatform.sdp

@Composable
fun SearchScreen(
    navigateToDetails: (Article?) -> Unit,
    searchNewsViewModel: SearchNewsViewModel,
    navController: NavHostController,
    status: State<ConnectivityObserver.Status>,
    infoDialog: MutableState<Boolean>,
) {
    Column(
        modifier = Modifier
            .padding(
                top = 5.sdp,
                start = 5.sdp,
                end = 5.sdp
            )
            .statusBarsPadding()
            .fillMaxSize()
    ) {

        SearchBar(
            text = "",
            hint = "Search",
            readOnly = false,
            onValueChange = {
                searchNewsViewModel.onEvent(SearchNewsEvent.UpdateSearchQuery(it.toString()))
            },
            onSearch = {
                searchNewsViewModel.onEvent(SearchNewsEvent.SearchNews)
            },
            searchNewsViewModel = searchNewsViewModel
        )

        when(status.value){
            ConnectivityObserver.Status.Available ->  infoDialog.value = false
            ConnectivityObserver.Status.Unavailable -> infoDialog.value = true
            ConnectivityObserver.Status.Losing -> infoDialog.value = false
            ConnectivityObserver.Status.Lost ->  infoDialog.value = true
            else -> infoDialog.value = false
        }
        var isLoading = rememberSaveable {
            mutableStateOf(true)
        }
        LaunchedEffect(Unit) {
            delay(2000)
            isLoading.value = false
        }
        if (infoDialog.value) {
            InfoDialog(
                tittle = "Whoops!",
                desc = "No Internet Connection found.\n" + "Check your connection or try again.",
                onDismiss = {
                    infoDialog.value = false
                }
            )
        }else{
            SearchItemScreen(searchNewsViewModel, navigateToDetails, navController,isLoading)
        }
    }
}

@Composable
fun SearchItemScreen(
    newsViewModel: SearchNewsViewModel,
    navigateToDetails: (Article?) -> Unit,
    navController: NavHostController,
    isLoading: MutableState<Boolean>,
) {

    LaunchedEffect(Unit) {
        newsViewModel.fetchTopHeadline()
    }

    val news = newsViewModel.newsSearchLiveData.observeAsState()
    val topNews = newsViewModel.topHeadNewsLiveData.observeAsState()

    Column(Modifier.fillMaxSize()) {
        Surface() {
            LazyColumn(
                modifier = Modifier
                    .wrapContentHeight()
            ) {
                if (newsViewModel.emptySearchState.value) {
                    if (news.value?.articles?.isNotEmpty() == true) {
                        items(news.value!!.articles) { it ->
                            ShimmerListItem(
                                isLoading = isLoading.value, contentAfterLoading = {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(CircleShape)
                                            .padding(16.sdp)
                                    ) {
                                        ArticleCard(article = it, onClick = {
                                            navigateToDetails(it)
                                        })
                                    }
                                }, modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            )
                        }
                    }

                } else {
                    if (topNews.value?.articles?.isNotEmpty() == true) {
                        items(topNews.value!!.articles) { it ->
                            ShimmerListItem(
                                isLoading = isLoading.value, contentAfterLoading = {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(CircleShape)
                                            .padding(16.sdp)
                                    ) {
                                        ArticleCard(
                                            article = it,
                                            onClick = { navigateToDetails(it) })
                                    }
                                }, modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            )
                        }
                    }
                }

            }
        }
    }

}




