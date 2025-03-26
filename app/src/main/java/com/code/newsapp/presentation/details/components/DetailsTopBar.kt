package com.code.newsapp.presentation.details.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import com.code.newsapp.R
import com.code.newsapp.presentation.news.model.response.Article
import com.code.newsapp.presentation.news.viewmodel.SearchNewsViewModel
import com.code.newsapp.ui.theme.NewsAppTheme
import com.code.newsapp.utils.Pref
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

    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp > 500  // Check if screen is tablet size

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
                val articlesToBookmark = listOf(Article(
                    article.author,
                    article.content,
                    article.description,
                    article.publishedAt,
                    article.source,
                    article.title,
                    article.url,
                    article.urlToImage
                ))
                if (isBookMark1.value) {
                    addBookmarks(articlesToBookmark)
                    Log.e("Datats", "true")
                } else {
                    removeBookmarks(articlesToBookmark)
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


fun addBookmarks(newArticles: List<Article>) {
    val bookmarks = Pref.getBookMarkArrayList("SaveBookMarkData").toMutableList()

    newArticles.forEach { article ->
        if (bookmarks.none { it.publishedAt == article.publishedAt }) {
            bookmarks.add(article) // Add only if it doesn't already exist
        }
    }

    Pref.saveBookMarkArrayList(bookmarks, "SaveBookMarkData")
}

fun removeBookmarks(articlesToRemove: List<Article>) {
    val bookmarks = Pref.getBookMarkArrayList("SaveBookMarkData").toMutableList()

    // Remove all specified articles
    bookmarks.removeAll { article ->
        articlesToRemove.any { it.publishedAt == article.publishedAt }
    }

    // Save the updated list back to preferences
    Pref.saveBookMarkArrayList(bookmarks, "SaveBookMarkData")
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