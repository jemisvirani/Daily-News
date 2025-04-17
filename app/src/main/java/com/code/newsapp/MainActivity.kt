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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.code.newsapp.all.checkinternet.NetworkConnectivityObserver
import com.code.newsapp.all.checkinternet.interfaces.ConnectivityObserver
import com.code.newsapp.all.news.api.RetrofitInstance
import com.code.newsapp.all.news.repository.NewsSearchRepository
import com.code.newsapp.all.news.utils.Pref
import com.code.newsapp.all.news.viewmodel.NewsViewModelFactory
import com.code.newsapp.all.news.viewmodel.SearchNewsViewModel
import com.code.newsapp.all.nvgraph.NavGraph
import com.code.newsapp.ui.theme.NewsAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : ComponentActivity() {
    private lateinit var connectivityObserver: ConnectivityObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityObserver = NetworkConnectivityObserver(applicationContext)
        connectivityObserver.observe().onEach {
        }.launchIn(lifecycleScope)

        setContent {
            NewsAppTheme {
                Pref.setInit(this)
                val isSystemInDarkMode = isSystemInDarkTheme()
                val systemController = rememberSystemUiController()

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
                        NewsSearchRepository(RetrofitInstance.getInstance(), status, infoDialog)
                    )
                )[SearchNewsViewModel::class.java]

                val newsItemBookMarkIcon = searchNewsViewModel.newsItemBookMarkIcon.collectAsState()
                val bookMarkData = searchNewsViewModel.bookMarkData.collectAsState()
                Log.e("BookMarkData", "onCreate1: ${newsItemBookMarkIcon.value}")
                Log.e("BookMarkData", "onCreate2: ${bookMarkData.value}")

                Surface(color = Color(0xFFFFFFFF), modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    NavGraph(
                        navController,
                        searchNewsViewModel,
                        status,
                        infoDialog
                    )
                }
            }
        }
    }
}






