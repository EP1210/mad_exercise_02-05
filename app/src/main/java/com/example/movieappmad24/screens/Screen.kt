package com.example.movieappmad24.screens

sealed class Screen(var route: String) {
    object Home : Screen(route = "home_screen")
    object Watchlist : Screen(route = "watchlist_screen")
    object Detail : Screen(route = "detail_screen")
}