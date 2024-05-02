package com.example.movieappmad24.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.models.MovieWithImages
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class WatchlistViewModel(private val movieRepository: MovieRepository) : ViewModel(), MovieViewModel {

    private val _favouriteMovies = MutableStateFlow(listOf<MovieWithImages>())
    val favouriteMovies: StateFlow<List<MovieWithImages>> = _favouriteMovies.asStateFlow()

    override fun updateFavouriteState(instance: MovieWithImages) {
        instance.movie.isFavourite = !instance.movie.isFavourite
        viewModelScope.launch {
            movieRepository.updateMovie(movie = instance.movie)
        }
    }

    init {
        viewModelScope.launch {
            movieRepository.getFavouriteMovies().distinctUntilChanged().collect { movies ->
                _favouriteMovies.value = movies
            }
        }
    }
}