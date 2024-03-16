package com.example.movieappmad24.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.movieappmad24.models.getWatchlistMovies

@Composable
fun WatchlistScreen(
    navigationController: NavController,
    route: String
) {
    ShowScreen(displayedMovies = getWatchlistMovies(), navigationController = navigationController, currentRoute = route)
}