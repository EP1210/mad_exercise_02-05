package com.example.movieappmad24.view_models

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies

class MovieViewModel : ViewModel() {

    private val _movies = getMovies().toMutableStateList()
    val movies: List<Movie>
        get() = _movies

    private val _favouriteMovies = mutableStateListOf<Movie>()
    val favouriteMovies: List<Movie>
        get() = _favouriteMovies

    fun toggleIsFavouriteState(movie: Movie) {
        movie.isFavourite = !movie.isFavourite
    }

    fun addToRemoveFromFavourites(movie: Movie) {
        if (movie.isFavourite) {
            _favouriteMovies.add(movie)
        } else {
            _favouriteMovies.remove(movie)
        }
    }
}