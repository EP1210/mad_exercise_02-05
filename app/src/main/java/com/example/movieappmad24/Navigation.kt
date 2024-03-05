package com.example.movieappmad24

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.models.getWatchlistMovies

@Composable
fun StartNavigation() {
    val navigationController = rememberNavController()

    NavHost(
        navController = navigationController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            ShowScreen(displayedMovies = getMovies(), navigationController = navigationController, currentRoute = Screen.Home.route)
        }
        composable(route = Screen.Watchlist.route) {
            ShowScreen(displayedMovies = getWatchlistMovies(), navigationController = navigationController, currentRoute = Screen.Watchlist.route)
        }
    }
}