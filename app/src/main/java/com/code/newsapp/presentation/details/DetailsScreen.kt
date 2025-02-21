package com.code.newsapp.presentation.details

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.code.newsapp.R
import com.code.newsapp.presentation.Dimens.ArticleImageHeight
import com.code.newsapp.presentation.Dimens.MediumPadding1
import com.code.newsapp.presentation.details.components.DetailsTopBar
import com.code.newsapp.presentation.news.model.response.Article
import com.code.newsapp.presentation.news.utils.Const
import com.code.newsapp.presentation.news.viewmodel.SearchNewsViewModel
import com.code.newsapp.ui.theme.NewsAppTheme
import com.code.newsapp.utils.Pref.getBookMarkArrayList

@Composable
fun DetailScreen(
    article: Article,
    navigateUp: () -> Unit,
    searchNewsViewModel: SearchNewsViewModel,
    articlesBookMark: State<List<Article?>>
) {


//    val isBookMark = remember {
//        mutableStateOf(false)
//    }

    val isBookMark = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        articlesBookMark.value.filter { it?.publishedAt == article.publishedAt }.forEach{ item ->
            Log.e("FetchData", "onCreate: ${item?.publishedAt == article.publishedAt}")
            isBookMark.value = item?.publishedAt == article.publishedAt
        }
    }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {

        DetailsTopBar(article,searchNewsViewModel,
            onBrowsingClick = {
                Intent(Intent.ACTION_VIEW).also {
                    it.data = Uri.parse(article.url)
                    if (it.resolveActivity(context.packageManager) != null) {
                        context.startActivity(it)
                    }
                }
            },
            onShareClick = {
                context.startActivity(Intent.createChooser(Intent(Intent.ACTION_SEND).also {
                    it.putExtra(Intent.EXTRA_TEXT, article.url)
                    it.type = "text/plain"
                },"NewsApp"))
            },
            onBookmarkClick = {
//                if (isBookMark.value){
//                    BookMarkData.removeBookMarkNews(article)
//                    isBookMark.value = false
//                }else{
//                    BookMarkData.saveBookMarkNews(article)
//                    isBookMark.value = true
//                }
            }, onBackClick = navigateUp,
            isBookMark
        )
//        event(DetailsEvent.UpsertDeleteArticle(article))
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(
                start = MediumPadding1,
                end = MediumPadding1,
                top = MediumPadding1
            )
        ) {
            item {
                AsyncImage(
                    model = ImageRequest.Builder(context = context).data(article.urlToImage)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(ArticleImageHeight)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(MediumPadding1))
                Text(
                    text = article.title.toString(),
                    style = MaterialTheme.typography.displaySmall,
                    color = colorResource(
                        id = R.color.text_title
                    )
                )
                Text(
                    text =
                    article.content.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(
                        id = R.color.body
                    )
                )
            }
        }
    }
}


fun clickBookMarks(article : Article) : Boolean{
    if (getBookMarkArrayList(Const.BOOKMARK).toString().isNotEmpty()){
        getBookMarkArrayList(Const.BOOKMARK).iterator().forEach {
            return article.source?.id.toString() == it.bookMark.toString()
        }
    }
    return false
}
@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    NewsAppTheme {
//        DetailScreen(
//            article = Article(
//                "",
//                "Coinbase says Apple blocked its last app release on NFTs in Wallet ... - CryptoSaurus",
//                "We use cookies and data to Deliver and maintain Google services Track outages and protect against spam, fraud, and abuse Measure audience engagement and site statistics to unde...",
//                "",
//                "",
//                Source(id = "", name = "bbc"),
//                url = "",
//                urlToImage = ""
//            ),
//            event = {}
//        ){
//
//        }
    }
}