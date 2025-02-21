package com.code.newsapp.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.code.newsapp.R
import com.code.newsapp.presentation.Dimens.ArticleCardSize
import com.code.newsapp.presentation.Dimens.ExtraSmallPadding
import com.code.newsapp.presentation.Dimens.ExtraSmallPadding2
import com.code.newsapp.presentation.Dimens.SmallIconSize
import com.code.newsapp.presentation.news.model.response.Article
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

@Composable
fun ArticleCard(
    article: Article?,
    onClick: () -> Unit
) {
    val context = LocalContext.current

    Row(modifier = Modifier.clickable { onClick() }) {
        AsyncImage(
            modifier = Modifier
                .size(96.dp)
                .clip(MaterialTheme.shapes.medium),
            model = ImageRequest.Builder(context).data(article?.urlToImage).build(),
            contentDescription = null, contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(ExtraSmallPadding2))

        Column(
            verticalArrangement = Arrangement.SpaceAround, modifier = Modifier
                .padding(horizontal = ExtraSmallPadding)
                .height(
                    ArticleCardSize
                )
        ) {

            Text(
                text = article?.title.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(
                    id = R.color.text_title
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = article?.source?.name.toString(),
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = colorResource(
                        id = R.color.body
                    )
                )

            }
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.ic_time),
                    contentDescription = null,
                    modifier = Modifier.size(SmallIconSize),
                    tint = colorResource(id = R.color.body)
                )
                Spacer(modifier = Modifier.width(ExtraSmallPadding2))
                Text(
                    text = getTimeAgo(article?.publishedAt.toString()),
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = colorResource(
                        id = R.color.body
                    )
                )
            }

        }
    }

}

fun getTimeAgo(isoTimestamp: String): String {
    // Define the input date format (ISO 8601)
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")

    // Parse the date string to a Date object
    val date: Date? = try {
        inputFormat.parse(isoTimestamp)
    } catch (e: Exception) {
        null
    }

    // If parsing fails, return an empty string
    date ?: return ""

    // Calculate the time difference
    val now = Date()
    val diffInMillis = now.time - date.time

    val minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis)
    val hours = TimeUnit.MILLISECONDS.toHours(diffInMillis)
    val days = TimeUnit.MILLISECONDS.toDays(diffInMillis)

    // Return the formatted time ago string
    return when {
        minutes < 1 -> "just now"
        minutes < 60 -> "$minutes minutes ago"
        hours < 24 -> "$hours hours ago"
        days < 7 -> "$days days ago"
        else -> {
            // Define the output date format for dates older than a week
            val outputFormat = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
            outputFormat.format(date)
        }
    }
}

fun convertToLocalDateTimeLegacy(isoDateTime: String): String {
    // Set up the SimpleDateFormat to parse the UTC time
    val utcFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    utcFormat.timeZone = TimeZone.getTimeZone("UTC")

    // Parse the date
    val date = utcFormat.parse(isoDateTime)

    // Set up another SimpleDateFormat for the local time zone
    val localFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    localFormat.timeZone = TimeZone.getDefault() // Device's local time zone

    // Format the date to the local time zone
    return localFormat.format(date)
}


//@Preview(showBackground = true)
//@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
//@Composable
//fun ArticleCardPreview() {
//    NewsAppTheme {
//        ArticleCard(
//            article = Article(
//                author = "",
//                content = "",
//                description = "",
//                publishedAt = "2 hours",
//                source = Source(id = "", name = "BBC"),
//                title = "Her traint broke down. Her phone died. And then she met her saver in a",
//                url = "",
//                urlToImage = ""
//            ),
//            articlesBookMark =
//        ) {
//
//        }
//    }
//}