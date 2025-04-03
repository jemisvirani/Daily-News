package com.code.newsapp.all.details

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.code.newsapp.R
import com.code.newsapp.all.details.components.DetailsTopBar
import com.code.newsapp.all.news.model.response.NewsItem
import com.code.newsapp.all.news.utils.Pref

@Composable
fun DetailScreen(
    newsItem: NewsItem,
    navigateUp: () -> Unit,
) {
    val isBookMark = remember { mutableStateOf(false) }
    val bookmarks = Pref.getBookMarkArrayList("SaveBookMarkData")
    if (bookmarks.isNotEmpty()) {
        val filteredBookmarks = bookmarks.filter { it.publishedAt == newsItem.publishedAt }

        Log.e("BookMarks", "DetailsTopBar: ${bookmarks.size}")
        filteredBookmarks.forEach { Log.e("BookMarks", "DetailsTopBar: $it") }
    }
    if (bookmarks.asSequence().any { it.publishedAt == newsItem.publishedAt }) {
        Log.e("BookMarks", "DetailsTopBar: Done")
        isBookMark.value = true
    } else {
        isBookMark.value = false
        Log.e("BookMarks", "DetailsTopBar: False")
    }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {

        DetailsTopBar(
            newsItem,
            onBrowsingClick = {
                Intent(Intent.ACTION_VIEW).also {
                    it.data = Uri.parse(newsItem.url)
                    if (it.resolveActivity(context.packageManager) != null) {
                        context.startActivity(it)
                    }
                }
            },
            onShareClick = {
                context.startActivity(Intent.createChooser(Intent(Intent.ACTION_SEND).also {
                    it.putExtra(Intent.EXTRA_TEXT, newsItem.url)
                    it.type = "text/plain"
                }, "NewsApp"))
            },
            onBookmarkClick = {
            }, onBackClick = navigateUp,
            isBookMark
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(
                start = 24.dp,
                end = 24.dp,
                top = 24.dp
            )
        ) {
            item {
                AsyncImage(
                    model = ImageRequest.Builder(context = context).data(newsItem.urlToImage)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(248.dp)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = newsItem.title.toString(),
                    style = MaterialTheme.typography.displaySmall,
                    color = colorResource(
                        id = R.color.text_title
                    )
                )
                Text(
                    text =
                    newsItem.content.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(
                        id = R.color.body
                    )
                )
            }
        }
    }
}