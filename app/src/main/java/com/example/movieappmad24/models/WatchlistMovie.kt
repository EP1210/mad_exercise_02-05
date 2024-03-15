package com.example.movieappmad24.models

import androidx.compose.runtime.mutableStateListOf

private val watchlistMovies = mutableStateListOf<Movie>()

fun addToOrRemoveFromWatchlist(movie: Movie) {
    if (movie !in watchlistMovies) {
        watchlistMovies.add(movie)
    } else {
        watchlistMovies.remove(movie)
    }
}

fun getWatchlistMovies(): List<Movie> {
    return watchlistMovies
}

fun watchlistContains(movie: Movie): Boolean {
    return movie in watchlistMovies
}