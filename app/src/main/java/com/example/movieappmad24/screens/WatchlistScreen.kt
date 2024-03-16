package com.example.movieappmad24.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.navigation.NavController
import com.example.movieappmad24.models.Movie

private val watchlistMovies = mutableStateListOf<Movie>()

@Composable
fun WatchlistScreen(
    navigationController: NavController,
    route: String
) {
    ShowScreen(displayedMovies = watchlistMovies, navigationController = navigationController, currentRoute = route)
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