package com.example.movieappmad24

sealed class Screen(var route: String) {
    object Home : Screen(route = "home_screen")
    object Watchlist : Screen(route = "watchlist_screen")
}