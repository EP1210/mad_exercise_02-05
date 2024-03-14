package com.example.movieappmad24.screens

import androidx.compose.runtime.Composable
import com.example.movieappmad24.models.getMovies

@Composable
fun DetailScreen(movieId: String?) {
    getMovies().forEach { movie ->
        if (movie.id == movieId) {
            // todo: implement detail screen logic
        }
    }
}