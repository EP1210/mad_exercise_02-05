package com.example.movieappmad24.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieappmad24.data.MovieRepository

class MovieViewModelFactory(private val movieRepository: MovieRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) { // https://stackoverflow.com/questions/69150992/how-can-i-create-common-viewmodelfactory-with-repository-in-kotlin-android
        HomeViewModel::class.java -> HomeViewModel(movieRepository = movieRepository)
        WatchlistViewModel::class.java -> WatchlistViewModel(movieRepository = movieRepository)
        DetailViewModel::class.java -> DetailViewModel(movieRepository = movieRepository)
        else -> throw IllegalArgumentException("Unknown ViewModel class!")
    } as T
}