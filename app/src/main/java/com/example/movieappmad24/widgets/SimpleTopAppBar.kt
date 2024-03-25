package com.example.movieappmad24.widgets

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.movieappmad24.ui.theme.Purple80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTopAppBar(
    navigationController: NavController? = null,
    title: String
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title
            )
        },
        navigationIcon = {
            if (navigationController != null) {
                SimpleEventIcon(
                    icon = Icons.Default.ArrowBack
                ) {
                    navigationController.popBackStack()
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Purple80
        )
    )
}