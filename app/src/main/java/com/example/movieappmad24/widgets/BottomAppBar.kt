package com.example.movieappmad24.widgets

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.movieappmad24.navigation.getBottomNavigationItems

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