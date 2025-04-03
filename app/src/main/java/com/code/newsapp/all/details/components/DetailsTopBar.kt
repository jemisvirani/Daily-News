package com.code.newsapp.all.details.components

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.code.newsapp.R
import com.code.newsapp.all.news.model.response.NewsItem
import com.code.newsapp.all.news.utils.Pref
import network.chaintech.sdpcomposemultiplatform.sdp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTopBar(
    newsItem: NewsItem,
    onBrowsingClick: () -> Unit,
    onShareClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onBackClick: () -> Unit,
    isBookMark1: MutableState<Boolean>,

    ) {

    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp > 500

    Log.e(
        "DetailsTopBar",
        "Width: ${configuration.screenWidthDp}, Height: ${configuration.screenHeightDp}"
    )
    val iconSize = if (isTablet) 16.sdp else 22.sdp

    TopAppBar(
        title = { },
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.Transparent, actionIconContentColor = colorResource(
                id = R.color.body,
            ), navigationIconContentColor = colorResource(id = R.color.body)
        ), navigationIcon = {
            IconButton(
                onClick = { onBackClick() },
                modifier = Modifier.padding(start = 5.sdp, top = 5.sdp, bottom = 4.sdp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back_arrow),
                    contentDescription = null,
                    tint = Color(0xFF4D861F),
                    modifier = Modifier.size(iconSize)
                )
            }
        },
        actions = {
            val context = LocalContext.current
            IconButton(onClick = {
                onBookmarkClick()
                isBookMark1.value = !isBookMark1.value
                val newsitemToBookmark = listOf(
                    NewsItem(
                        newsItem.author,
                        newsItem.content,
                        newsItem.description,
                        newsItem.publishedAt,
                        newsItem.source,
                        newsItem.title,
                        newsItem.url,
                        newsItem.urlToImage
                    )
                )
                if (isBookMark1.value) {
                    addBookmarks(newsitemToBookmark)
                    Log.e("Datats", "true")
                } else {
                    removeBookmarks(newsitemToBookmark)
                    Log.e("Datats", "false")
                }
            }) {
                Icon(
                    imageVector = if (isBookMark1.value) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                    contentDescription = null,
                    tint = if (isBookMark1.value) Color(0xFF4D861F) else Color(0xFF4D861F),
                    modifier = Modifier.size(iconSize)
                )
            }
            IconButton(onClick = { onShareClick() }) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = null,
                    tint = Color(0xFF4D861F),
                    modifier = Modifier.size(iconSize)
                )
            }
            IconButton(onClick = { onBrowsingClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_network),
                    contentDescription = null,
                    tint = Color(0xFF4D861F),
                    modifier = Modifier
                        .size(iconSize)
                        .padding(end = 4.sdp)
                )
            }
        }
    )
}


fun addBookmarks(newsItem: List<NewsItem>) {
    val bookmarks = Pref.getBookMarkArrayList("SaveBookMarkData").toMutableList()

    newsItem.forEach { newsItem ->
        if (bookmarks.none { it.publishedAt == newsItem.publishedAt }) {
            bookmarks.add(newsItem)
        }
    }

    Pref.saveBookMarkArrayList(bookmarks, "SaveBookMarkData")
}

fun removeBookmarks(newsItemToRemove: List<NewsItem>) {
    val bookmarks = Pref.getBookMarkArrayList("SaveBookMarkData").toMutableList()

    bookmarks.removeAll { newsItem ->
        newsItemToRemove.any { it.publishedAt == newsItem.publishedAt }
    }

    Pref.saveBookMarkArrayList(bookmarks, "SaveBookMarkData")
}
