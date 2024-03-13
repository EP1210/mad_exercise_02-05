package com.example.movieappmad24.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.movieappmad24.screens.Screen

data class BottomNavigationItem(
    val label: String,
    val selected: ImageVector,
    val unselected: ImageVector,
    val route: String
)

fun getBottomNavigationItems(): List<BottomNavigationItem> {
    return listOf(
        BottomNavigationItem(
            label = "Home",
            selected = Icons.Filled.Home,
            unselected = Icons.Outlined.Home,
            route = Screen.Home.route
        ),
        BottomNavigationItem(
            label = "Watchlist",
            selected = Icons.Filled.Star,
            unselected = Icons.Outlined.Star,
            route = Screen.Watchlist.route
        )
    )
}