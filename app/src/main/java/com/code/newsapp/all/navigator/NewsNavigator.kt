package com.code.newsapp.all.navigator

import android.util.Log
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.code.newsapp.R
import com.code.newsapp.all.bookmark.BookmarkScreen
import com.code.newsapp.all.checkinternet.interfaces.ConnectivityObserver
import com.code.newsapp.all.details.DetailScreen
import com.code.newsapp.all.home.HomeScreen
import com.code.newsapp.all.navigator.components.BottomNavigationItem
import com.code.newsapp.all.navigator.components.NewsBottomNavigation
import com.code.newsapp.all.news.model.response.NewsItem
import com.code.newsapp.all.news.viewmodel.SearchNewsViewModel
import com.code.newsapp.all.nvgraph.Route

@Composable
fun NewsNavigator(
    searchNewsViewModel: SearchNewsViewModel,
    status: State<ConnectivityObserver.Status>,
    infoDialog: MutableState<Boolean>,
) {

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
        backStackState?.destination?.route == Route.HomeScreen.route ||
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
                        animationSpec = tween(700)
                    ) + fadeOut(animationSpec = tween(700))
                }) {
                HomeScreen(
                    navigateToDetails = { it -> navigateToDetails(navController, it) },
                    searchNewsViewModel = searchNewsViewModel, status, infoDialog
                )
            }
            composable(route = Route.DetailScreen.route, enterTransition = {
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
                        animationSpec = tween(700)
                    ) + fadeOut(animationSpec = tween(700))
                }) {
                navController.previousBackStackEntry?.savedStateHandle?.get<NewsItem?>("newsItem")
                    ?.let { newsItem ->
                        DetailScreen(
                            newsItem = newsItem,
                            navigateUp = { navController.navigateUp() }
                        )
                    }
            }

            composable(route = Route.BookmarkScreen.route, enterTransition = {
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
                        animationSpec = tween(700)
                    ) + fadeOut(animationSpec = tween(700))
                }) {
                BookmarkScreen(navigateToDetails = { newsItem ->
                    navigateToDetails(navController, newsItem)
                }, status)
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

private fun navigateToDetails(navController: NavController, newsItem: NewsItem?) {
    Log.e("navigateToDetails", "navigateToDetails: ${newsItem?.title}")
    navController.currentBackStackEntry?.savedStateHandle?.set("newsItem", newsItem)
    navController.navigate(route = Route.DetailScreen.route)
}