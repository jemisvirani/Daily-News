package com.code.newsapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.code.newsapp.checkinternet.interfaces.ConnectivityObserver
import com.code.newsapp.data.local.NewsDao
import com.code.newsapp.presentation.checkinternet.NetworkConnectivityObserver
import com.code.newsapp.presentation.news.api.RetrofitInstance
import com.code.newsapp.presentation.news.repository.NewsSearchRepository
import com.code.newsapp.presentation.news.viewmodel.SearchNewsViewModel
import com.code.newsapp.presentation.news.viewmodel.SearchNewsViewModelFactory
import com.code.newsapp.presentation.nvgraph.NavGraph
import com.code.newsapp.ui.theme.NewsAppTheme
import com.code.newsapp.utils.Pref
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel by viewModels<MainViewModel>()
    @Inject
    lateinit var dao : NewsDao
    private lateinit var connectivityObserver: ConnectivityObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityObserver = NetworkConnectivityObserver(applicationContext)
        connectivityObserver.observe().onEach {
            println("Status is $it")
        }.launchIn(lifecycleScope)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val searchNewsViewModel = ViewModelProvider(
            this, SearchNewsViewModelFactory(
                NewsSearchRepository(
                    RetrofitInstance.getInstance()
                )
            )
        )[SearchNewsViewModel::class.java]

        searchNewsViewModel.newsSearchLiveData.observe(this, Observer { it ->
//            Toast.makeText(this, it.articles.toString(), Toast.LENGTH_SHORT).show()
        })

        searchNewsViewModel.topHeadNewsLiveData.observe(this, Observer{
            it ->
//            Log.e("DailyNews", "onCreate: ${it.articles}")
        })



        setContent {
            NewsAppTheme {
                Pref.setInit(this)
                val isSystemInDarkMode = isSystemInDarkTheme()
                val systemController = rememberSystemUiController()
                val articlesBookMarkIcon = searchNewsViewModel.articlesBookMarkIcon.collectAsState()
                val topNewsData = searchNewsViewModel.topItemData.collectAsState()

                Log.e("TopItemData", "onCreate: ${topNewsData.value}")
                SideEffect {
                     systemController.setSystemBarsColor(color = Color.Transparent,darkIcons = !isSystemInDarkMode)
                }


                val status = connectivityObserver.observe()
                    .collectAsState(initial = ConnectivityObserver.Status.Unavailable)


                Surface(color = Color(0xFFFFFFFF), modifier = Modifier.fillMaxSize()) {
                    val startDestination = viewModel.startDestination
                    val navController = rememberNavController()
                    NavGraph(navController,searchNewsViewModel,articlesBookMarkIcon,articlesBookMarkIcon,status)
                }






            }
        }
    }
}





