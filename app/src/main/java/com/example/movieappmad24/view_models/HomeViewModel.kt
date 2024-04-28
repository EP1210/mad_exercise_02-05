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

    fun addToRemoveFromFavourites(instance: MovieWithImages) = WatchlistViewModel(movieRepository = movieRepository).addToRemoveFromFavourites(instance = instance)

    fun updateFavouriteState(instance: MovieWithImages) = WatchlistViewModel(movieRepository = movieRepository).updateFavouriteState(instance = instance)

    init {
        viewModelScope.launch {
            movieRepository.getAllMovies().distinctUntilChanged().collect { movies ->
                _allMovies.value = movies
            }
        }
    }
}