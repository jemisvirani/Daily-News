package com.code.newsapp.all.nvgraph

sealed class Route(val route: String) {
    object HomeScreen : Route(route = "homeScreen")
    object BookmarkScreen : Route(route = "bookmarkScreen")
    object DetailScreen : Route(route = "detailsScreen")
}
