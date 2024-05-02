package com.example.movieappmad24.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.models.MovieWithImages
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val movieRepository: MovieRepository) : ViewModel(), MovieViewModel {

    private val _specificMovie: MutableStateFlow<MovieWithImages?>? = null

    fun searchMovieById(movieId: Long?): StateFlow<MovieWithImages?>? {
        viewModelScope.launch {
            movieRepository.getMovieById(movieId = movieId).collect { movie ->
                _specificMovie?.value = movie
            }
        }
        return _specificMovie?.asStateFlow()
    }

    override fun updateFavouriteState(instance: MovieWithImages) {
        instance.movie.isFavourite = !instance.movie.isFavourite
        viewModelScope.launch {
            movieRepository.updateMovie(movie = instance.movie)
        }
    }
}