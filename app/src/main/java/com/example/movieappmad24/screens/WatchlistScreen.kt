package com.example.movieappmad24.screens

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movieappmad24.dependency_injection.InjectorUtils
import com.example.movieappmad24.view_models.WatchlistViewModel
import com.example.movieappmad24.widgets.SimpleBottomAppBar
import com.example.movieappmad24.widgets.SimpleTopAppBar

@Composable
fun WatchlistScreen(
    navigationController: NavController,
    route: String
) {
    val watchlistViewModel: WatchlistViewModel = viewModel(factory = InjectorUtils.provideMovieViewModelFactory(context = LocalContext.current))

Scaffold(
        topBar = {
            SimpleTopAppBar(title = "Your Watchlist")
        },
        bottomBar = {
            SimpleBottomAppBar(navigationController = navigationController, currentRoute = route)
        }
    ) {
        MovieList(
            movies = watchlistViewModel.favouriteMovies.collectAsState().value,
            viewModel = watchlistViewModel,
            padding = it,
            navigationController = navigationController
        )
    }
}