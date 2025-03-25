package com.code.newsapp.presentation.bookmark

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.code.newsapp.R
import com.code.newsapp.checkinternet.interfaces.ConnectivityObserver
import com.code.newsapp.presentation.common.ArticleCard
import com.code.newsapp.presentation.dilaog.InfoDialog
import com.code.newsapp.presentation.news.model.response.Article
import com.code.newsapp.presentation.shimmer.ShimmerListItem
import com.code.newsapp.utils.Pref
import kotlinx.coroutines.delay
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp

@Composable
fun BookmarkScreen(
    state: State<List<Article?>>,
    navigateToDetails: (Article?) -> Unit,
    status: State<ConnectivityObserver.Status>
) {

    var isLoading = rememberSaveable {
        mutableStateOf(true)
    }

    LaunchedEffect(Unit) {
        delay(1000)
        isLoading.value = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(top = 10.sdp, start = 15.sdp, end = 15.sdp)
    ) {
        Text(
            text = "Bookmark",
            fontSize = 22.ssp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.text_title),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.sdp))

        val infoDialog = remember {
            mutableStateOf(false)
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
            Column(Modifier.fillMaxSize()) {
                if (Pref.getBookMarkArrayList("SaveBookMarkData").isEmpty()){
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Data not found", color = colorResource(R.color.green), fontSize = 15.ssp)
                    }
                }else{
                    Surface() {
                        LazyColumn(
                            modifier = Modifier
                                .wrapContentHeight()
                        ) {
                            items(Pref.getBookMarkArrayList("SaveBookMarkData").size) { index ->
                                ShimmerListItem(
                                    isLoading = isLoading.value, contentAfterLoading = {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clip(CircleShape)
                                                .padding(16.sdp)
                                        ) {
                                            ArticleCard(article = Pref.getBookMarkArrayList("SaveBookMarkData")[index], onClick = {
                                                navigateToDetails(Pref.getBookMarkArrayList("SaveBookMarkData")[index])
                                            })
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

        when(status.value){
            ConnectivityObserver.Status.Available ->  infoDialog.value = false
            ConnectivityObserver.Status.Unavailable -> infoDialog.value = true
            ConnectivityObserver.Status.Losing -> infoDialog.value = false
            ConnectivityObserver.Status.Lost ->  infoDialog.value = true
            else -> infoDialog.value = false
        }

    }

}