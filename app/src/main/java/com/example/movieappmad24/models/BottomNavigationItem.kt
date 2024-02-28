package com.example.movieappmad24.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Star
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val label: String,
    val selected: ImageVector,
    val unselected: ImageVector,
)

fun getBottomNavigationItems(): List<BottomNavigationItem> {
    return listOf(
        BottomNavigationItem(
            label = "Home",
            selected = Icons.Filled.Home,
            unselected = Icons.Outlined.Home
        ),
        BottomNavigationItem(
            label = "Watchlist",
            selected = Icons.Filled.Star,
            unselected = Icons.Outlined.Star
        )
    )
}