package com.example.movieappmad24.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.movieappmad24.models.getMovies

@Composable
fun DetailScreen(movieId: String?) {
    getMovies().forEach { movie ->
        if (movie.id == movieId) {
            Text(
                text = "Hello detailscreen $movieId"
            )
        }
    }
}