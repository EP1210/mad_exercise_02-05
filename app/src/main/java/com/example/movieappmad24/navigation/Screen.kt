package com.example.movieappmad24.navigation

const val MOVIE_DETAIL_KEY = "movieId"

sealed class Screen(var route: String) {
    object Home : Screen(route = "home_screen")
    object Watchlist : Screen(route = "watchlist_screen")
    object Detail : Screen(route = "detail_screen/{$MOVIE_DETAIL_KEY}") {
        fun passMovieId(movieId: String): String {
            return this.route.replace(oldValue = "{$MOVIE_DETAIL_KEY}", newValue = movieId)
        }
    }
}