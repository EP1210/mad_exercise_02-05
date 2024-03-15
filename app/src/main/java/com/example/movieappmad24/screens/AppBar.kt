package com.example.movieappmad24.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.navigation.Screen
import com.example.movieappmad24.navigation.getBottomNavigationItems
import com.example.movieappmad24.ui.theme.Purple80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTopAppBar(
    navigationController: NavController? = null,
    currentRoute: String,
    movie: Movie? = null
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = when (currentRoute) {
                    Screen.Watchlist.route -> "Your Watchlist"
                    Screen.Detail.route -> movie?.title ?: ""
                    else -> "Movie App"
                }
            )
        },
        navigationIcon = {
            if (navigationController != null) {
                IconButton(
                    onClick = {
                        navigationController.popBackStack()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Purple80
        )
    )
}

@Composable
fun SimpleBottomAppBar(
    navigationController: NavController,
    currentRoute: String
) {
    NavigationBar {
        getBottomNavigationItems().forEach { navigationItem ->
            NavigationBarItem(
                selected = navigationItem.route == currentRoute,
                onClick = {
                    navigationController.navigate(route = navigationItem.route) {
                        popUpTo(id = 0)
                    }
                },
                icon = {
                    Icon(
                        imageVector = when (navigationItem.route) {
                            currentRoute -> navigationItem.selected
                            else -> navigationItem.unselected
                        },
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = navigationItem.label
                    )
                }
            )
        }
    }
}