package com.code.newsapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.code.newsapp.checkinternet.interfaces.ConnectivityObserver
import com.code.newsapp.presentation.checkinternet.NetworkConnectivityObserver
import com.code.newsapp.presentation.news.api.RetrofitInstance
import com.code.newsapp.presentation.news.repository.NewsSearchRepository
import com.code.newsapp.presentation.news.viewmodel.NewsViewModelFactory
import com.code.newsapp.presentation.news.viewmodel.SearchNewsViewModel
import com.code.newsapp.presentation.nvgraph.NavGraph
import com.code.newsapp.ui.theme.NewsAppTheme
import com.code.newsapp.utils.Pref
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : ComponentActivity() {
    private lateinit var connectivityObserver: ConnectivityObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityObserver = NetworkConnectivityObserver(applicationContext)
        connectivityObserver.observe().onEach {
//            println("Status is $it")
        }.launchIn(lifecycleScope)
        WindowCompat.setDecorFitsSystemWindows(window, false)
//        searchNewsViewModel.newsSearchLiveData.observe(this, Observer { it ->
////            Toast.makeText(this, it.articles.toString(), Toast.LENGTH_SHORT).show()
//        })

//        searchNewsViewModel.topHeadNewsLiveData.observe(this, Observer{
//            it ->
////            Log.e("DailyNews", "onCreate: ${it.articles}")
//        })

        setContent {
            NewsAppTheme {
                Pref.setInit(this)
                val isSystemInDarkMode = isSystemInDarkTheme()
                val systemController = rememberSystemUiController()


                val context = LocalContext.current
//                Log.e("TopItemData", "onCreate: ${topNewsData.value}")
                SideEffect {
                    systemController.setSystemBarsColor(
                        color = Color.Transparent,
                        darkIcons = !isSystemInDarkMode
                    )
                }

                val status = connectivityObserver.observe()
                    .collectAsState(initial = ConnectivityObserver.Status.Unavailable)

                val infoDialog = remember {
                    mutableStateOf(false)
                }


                val searchNewsViewModel = ViewModelProvider(
                    this,
                    NewsViewModelFactory(
                        this,
                        NewsSearchRepository(RetrofitInstance.getInstance(),status,infoDialog)
                    )
                )[SearchNewsViewModel::class.java]

                val articlesBookMarkIcon = searchNewsViewModel.articlesBookMarkIcon.collectAsState()
                val bookMarkData = searchNewsViewModel.bookMarkData.collectAsState()
                Log.e("BookMarkData", "onCreate1: ${articlesBookMarkIcon.value}")
                Log.e("BookMarkData", "onCreate2: ${bookMarkData.value}")
                val topNewsData = searchNewsViewModel.topItemData.collectAsState()


                Surface(color = Color(0xFFFFFFFF), modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    NavGraph(
                        navController,
                        searchNewsViewModel,
                        articlesBookMarkIcon,
                        articlesBookMarkIcon,
                        status,
                        infoDialog
                    )
                }
            }
        }
    }
}






