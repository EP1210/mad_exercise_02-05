package com.example.movieappmad24.screens

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.movieappmad24.view_models.MovieViewModel
import com.example.movieappmad24.widgets.SimpleBottomAppBar
import com.example.movieappmad24.widgets.SimpleTopAppBar

@Composable
fun WatchlistScreen(
    navigationController: NavController,
    route: String,
    viewModel: MovieViewModel
) {
    Scaffold(
        topBar = {
            SimpleTopAppBar(title = "Your Watchlist")
        },
        bottomBar = {
            SimpleBottomAppBar(navigationController = navigationController, currentRoute = route)
        }
    ) {
        MovieList(
            movies = viewModel.favouriteMovies,
            viewModel = viewModel,
            padding = it,
            navigationController = navigationController
        )
    }
}