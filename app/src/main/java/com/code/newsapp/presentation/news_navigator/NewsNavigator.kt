package com.code.newsapp.presentation.news_navigator

import android.util.Log
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.code.newsapp.R
import com.code.newsapp.checkinternet.interfaces.ConnectivityObserver
import com.code.newsapp.presentation.bookmark.BookMarkViewModel
import com.code.newsapp.presentation.bookmark.BookmarkScreen
import com.code.newsapp.presentation.details.DetailScreen
import com.code.newsapp.presentation.home.HomeScreen
import com.code.newsapp.presentation.news.model.response.Article
import com.code.newsapp.presentation.news.viewmodel.SearchNewsViewModel
import com.code.newsapp.presentation.news_navigator.components.BottomNavigationItem
import com.code.newsapp.presentation.news_navigator.components.NewsBottomNavigation
import com.code.newsapp.presentation.nvgraph.Route
import kotlin.collections.List

@Composable
fun NewsNavigator(
    searchNewsViewModel: SearchNewsViewModel,
    articlesBookMark: State<List<Article?>>,
    navController1: NavHostController,
    articlesBookMarkIcon: State<List<Article?>>,
    status: State<ConnectivityObserver.Status>,
) {

    //   राधा

    val bottomNavigationItem = remember {
        listOf(
            BottomNavigationItem(
                icon = R.drawable.ic_home, text = "Home"
            ), BottomNavigationItem(
                icon = R.drawable.ic_bookmark_unselect, text = "Bookmark"
            )
        )
    }

    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem = rememberSaveable {
        mutableStateOf(0)
    }

    selectedItem.value = remember(key1 = backStackState) {
        when (backStackState?.destination?.route) {
            Route.HomeScreen.route -> 0
            Route.BookmarkScreen.route -> 1
            else -> 0
        }
    }

    val isBottomBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Route.HomeScreen.route  ||
                backStackState?.destination?.route == Route.BookmarkScreen.route
    }

    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        if (isBottomBarVisible) {
            NewsBottomNavigation(items = bottomNavigationItem,
                selected = selectedItem.value,
                onItemClick = { index ->
                    when (index) {
                        0 -> navigateToTop(
                            navController = navController, route = Route.HomeScreen.route
                        )
                        1 -> navigateToTop(
                            navController = navController, route = Route.BookmarkScreen.route
                        )
                    }
                }
            )
        }
    }
    ) {

        val context = LocalContext.current.applicationContext

        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable(
                route = Route.HomeScreen.route,
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(700)
                    ) + fadeIn(animationSpec = tween(700))
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(700)
                    ) + fadeOut(animationSpec = tween(700))
                },
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(700)
                    ) + fadeIn(animationSpec = tween(700))
                },
                popExitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(700)) + fadeOut(animationSpec = tween(700))
                }) {



                HomeScreen(
                    navigateToDetails = { it -> navigateToDetails(navController, it) },
                    searchNewsViewModel = searchNewsViewModel, navController,status
                )






            }
            composable(route = Route.DetailScreen.route,enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(700)
                ) + fadeIn(animationSpec = tween(700))
            },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(700)
                    ) + fadeOut(animationSpec = tween(700))
                },
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(700)
                    ) + fadeIn(animationSpec = tween(700))
                },
                popExitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(700)) + fadeOut(animationSpec = tween(700))
                }) {
//                val viewModel: DetailViewModel = hiltViewModel()
//                if (viewModel.sideEffect.value != null) {
//                    Toast.makeText(
//                        LocalContext.current,
//                        viewModel.sideEffect.value,
//                        Toast.LENGTH_SHORT
//                    )
//                        .show()
//                    viewModel.onEvent(DetailsEvent.RemoveSideEffect)
//                }
                navController.previousBackStackEntry?.savedStateHandle?.get<Article?>("article")
                    ?.let { article ->
                        DetailScreen(
                            article = article,
                            navigateUp = { navController.navigateUp() },
                            searchNewsViewModel,
                            articlesBookMark = articlesBookMark
                        )
                    }
            }

            composable(route = Route.BookmarkScreen.route) {
                val viewModel: BookMarkViewModel = hiltViewModel()
                val state = viewModel.state.value
                BookmarkScreen(state = articlesBookMarkIcon, navigateToDetails = { article ->
                    navigateToDetails(navController, article)
                })
            }
        }
    }
}

private fun navigateToTop(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }
}

private fun navigateToDetails(navController: NavController, article: Article?) {
    Log.e("navigateToDetails", "navigateToDetails: ${article?.title}")
    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
    navController.navigate(route = Route.DetailScreen.route)
//    navController.previousBackStackEntry?.destination?.route?.let { route ->
//        Log.e("BackData", "navigateToDetails: $route")
//    }
}