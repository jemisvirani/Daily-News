package com.code.newsapp.presentation.bookmark

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.code.newsapp.R
import com.code.newsapp.domain.model.Article
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp

@Composable
fun BookmarkScreen(state: BookmarkState, navigateToDetails: (Article) -> Unit) {
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

//        ArticlesList(articles = state.articles, onClick = {navigateToDetails(it)})
    }

}