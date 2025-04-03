package com.code.newsapp.all.search

import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.code.newsapp.all.checkinternet.interfaces.ConnectivityObserver
import com.code.newsapp.all.dilaog.InfoDialog
import com.code.newsapp.all.news.item.NewsItem
import com.code.newsapp.all.news.model.response.NewsItem
import com.code.newsapp.all.news.search.SearchNewsEvent
import com.code.newsapp.all.news.searchscreen.SearchBar
import com.code.newsapp.all.news.viewmodel.SearchNewsViewModel
import com.code.newsapp.all.shimmer.ShimmerListItem
import kotlinx.coroutines.delay
import network.chaintech.sdpcomposemultiplatform.sdp

@Composable
fun SearchScreen(
    navigateToDetails: (NewsItem?) -> Unit,
    searchNewsViewModel: SearchNewsViewModel,
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

        when (status.value) {
            ConnectivityObserver.Status.Available -> infoDialog.value = false
            ConnectivityObserver.Status.Unavailable -> infoDialog.value = true
            ConnectivityObserver.Status.Losing -> infoDialog.value = false
            ConnectivityObserver.Status.Lost -> infoDialog.value = true
            else -> infoDialog.value = false
        }
        var isLoading = rememberSaveable {
            mutableStateOf(true)
        }
        LaunchedEffect(searchNewsViewModel) {
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
        } else {
            SearchItemScreen(searchNewsViewModel, navigateToDetails, isLoading)
        }
    }
}

@Composable
fun SearchItemScreen(
    newsViewModel: SearchNewsViewModel,
    navigateToDetails: (NewsItem?) -> Unit,
    isLoading: MutableState<Boolean>,
) {

    LaunchedEffect(Unit) {
        newsViewModel.fetchTopHeadline()
    }

    val news = newsViewModel.newsSearchLiveData.observeAsState()
    val topNews = newsViewModel.topHeadNewsLiveData.observeAsState()

    Column(Modifier.fillMaxSize()) {
        Surface() {
            LazyColumn(modifier = Modifier.wrapContentHeight()) {
                val newsItem =
                    if (newsViewModel.emptySearchState.value) news.value?.articles else topNews.value?.articles
                if (newsItem.isNullOrEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No news found",
                                modifier = Modifier
                                    .padding(16.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                } else {
                    items(newsItem) { newsItem ->
                        ShimmerListItem(
                            isLoading = isLoading.value,
                            contentAfterLoading = {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(CircleShape)
                                        .padding(16.sdp)
                                ) {
                                    NewsItem(
                                        newsItem = newsItem,
                                        onClick = { navigateToDetails(newsItem) })
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                }
            }

        }
    }

}




