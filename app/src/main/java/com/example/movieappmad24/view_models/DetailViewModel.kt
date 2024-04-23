package com.example.movieappmad24.view_models

import androidx.lifecycle.ViewModel
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.models.MovieWithImages

class DetailViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun searchMovieById(movieId: Long?): MovieWithImages? {
        return movieRepository.getMovieById(movieId = movieId)
    }

    fun toggleIsFavouriteState(instance: MovieWithImages) = HomeViewModel(movieRepository = movieRepository).toggleIsFavouriteState(instance = instance)
}