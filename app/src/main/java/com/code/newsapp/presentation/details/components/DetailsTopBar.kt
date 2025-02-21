package com.code.newsapp.presentation.details.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.code.newsapp.R
import com.code.newsapp.presentation.firebase.BookMarkData.removeBookMarkNews
import com.code.newsapp.presentation.firebase.BookMarkData.saveBookMarkNews
import com.code.newsapp.presentation.news.model.response.Article
import com.code.newsapp.presentation.news.viewmodel.SearchNewsViewModel
import com.code.newsapp.ui.theme.NewsAppTheme
import network.chaintech.sdpcomposemultiplatform.sdp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTopBar(
    article: Article,
    searchNewsViewModel: SearchNewsViewModel,
    onBrowsingClick: () -> Unit,
    onShareClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onBackClick: () -> Unit,
    isBookMark1: MutableState<Boolean>,

    ) {
    val isBookMark = remember {
        mutableStateOf(false)
    }
    TopAppBar(
        title = { },
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.Transparent, actionIconContentColor = colorResource(
                id = R.color.body,
            ), navigationIconContentColor = colorResource(id = R.color.body)
        ), navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back_arrow),
                    contentDescription = null,
                    tint = Color(0xFF4D861F),
                    modifier = Modifier.size(16.sdp)
                )
            }
        },
        actions = {
            IconButton(onClick = { onBookmarkClick()
                isBookMark1.value = !isBookMark1.value
                if (isBookMark1.value){
                    saveBookMarkNews(article)
                }else{
                    removeBookMarkNews(article)
                }
            }) {
                Icon(
                    imageVector = if (isBookMark1.value) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                    contentDescription = null,
                    tint = if (isBookMark1.value) Color(0xFF4D861F) else Color(0xFF4D861F),
                    modifier = Modifier.size(24.sdp)
                )
            }
            IconButton(onClick = { onShareClick() }) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = null,
                    tint = Color(0xFF4D861F)
                )
            }
            IconButton (onClick = { onBrowsingClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_network),
                    contentDescription = null,
                    tint = Color(0xFF4D861F)
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DetailsTopBarPreview() {
    NewsAppTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {

        }
//        DetailsTopBar(
//            onBrowsingClick = {  },
//            onShareClick = {  },
//            onBookmarkClick = {  })
//        {
//
//        }
    }
}