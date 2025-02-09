package com.code.newsapp.presentation.nvgraph

sealed class Route(val route : String){
    object OnBoardingScreen : Route(route = "onBoardingScreen")
    object HomeScreen : Route(route = "homeScreen")
    object SearchScreen : Route(route = "searchScreen")
    object BookmarkScreen : Route(route = "bookmarkScreen")
    object DetailScreen : Route(route = "detailsScreen")
    object NewsNavigation : Route(route = "newsNavigation")
    object NewsNavigatorScreen : Route(route = "newsNavigator")
    object SplashNavigatorScreen : Route(route = "splashScreen")
}
