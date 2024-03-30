package com.example.movieappmad24.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieappmad24.MovieViewModel
import com.example.movieappmad24.screens.DetailScreen
import com.example.movieappmad24.screens.HomeScreen
import com.example.movieappmad24.screens.WatchlistScreen

@Composable
fun Navigation() {
    val navigationController = rememberNavController()
    val movieViewModel: MovieViewModel = viewModel()

    NavHost(
        navController = navigationController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                navigationController = navigationController,
                route = Screen.Home.route,
                viewModel = movieViewModel
            )
        }
        composable(route = Screen.Watchlist.route) {
            WatchlistScreen(
                navigationController = navigationController,
                route = Screen.Watchlist.route,
                viewModel = movieViewModel
            )
        }
        composable(route = Screen.Detail.route) { backStackEntry ->
            DetailScreen(
                movieId = backStackEntry.arguments?.getString(MOVIE_DETAIL_KEY),
                navigationController = navigationController,
                viewModel = movieViewModel
            )
        }
    }
}