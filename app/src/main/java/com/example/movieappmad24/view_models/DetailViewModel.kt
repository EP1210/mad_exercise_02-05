package com.example.movieappmad24.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.models.MovieWithImages
import kotlinx.coroutines.launch

class DetailViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun searchMovieById(movieId: Long?): MovieWithImages? {
        var movie: MovieWithImages? = null

        viewModelScope.launch {
            movie = movieRepository.getMovieById(movieId = movieId)
        }
        return movie
    }

    fun addToRemoveFromFavourites(instance: MovieWithImages) = WatchlistViewModel(movieRepository = movieRepository).addToRemoveFromFavourites(instance = instance)

    fun updateFavouriteState(instance: MovieWithImages) = WatchlistViewModel(movieRepository = movieRepository).updateFavouriteState(instance = instance)
}