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

open class HomeViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private val _allMovies = MutableStateFlow(listOf<MovieWithImages>())
    val allMovies: StateFlow<List<MovieWithImages>> = _allMovies.asStateFlow()

    fun toggleIsFavouriteState(instance: MovieWithImages) {
        viewModelScope.launch {
            instance.movie.isFavourite = !instance.movie.isFavourite
            movieRepository.updateMovie(movie = instance.movie)
        }
    }

    init {
        viewModelScope.launch {
            movieRepository.getAllMovies().distinctUntilChanged().collect { movies ->
                _allMovies.value = movies
            }
        }
    }
}