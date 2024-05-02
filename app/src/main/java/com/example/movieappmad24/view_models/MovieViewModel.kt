package com.example.movieappmad24.view_models

import com.example.movieappmad24.models.MovieWithImages

interface MovieViewModel {

    fun updateFavouriteState(instance: MovieWithImages)
}