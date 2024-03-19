package com.example.movieappmad24.screens

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.navigation.NavController
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.widgets.SimpleBottomAppBar
import com.example.movieappmad24.widgets.SimpleTopAppBar

private val watchlistMovies = mutableStateListOf<Movie>()

@Composable
fun WatchlistScreen(
    navigationController: NavController,
    route: String
) {
    Scaffold(
        topBar = {
            SimpleTopAppBar(topAppBarTitle = "Your Watchlist")
        },
        bottomBar = {
            SimpleBottomAppBar(navigationController = navigationController, currentRoute = route)
        }
    ) {
        MovieList(movies = watchlistMovies, padding = it, navigationController = navigationController)
    }
}

fun addToOrRemoveFromWatchlist(movie: Movie) {
    if (movie !in watchlistMovies) {
        watchlistMovies.add(movie)
    } else {
        watchlistMovies.remove(movie)
    }
}

fun watchlistContains(movie: Movie): Boolean {
    return movie in watchlistMovies
}