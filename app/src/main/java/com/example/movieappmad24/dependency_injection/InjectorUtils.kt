package com.example.movieappmad24.dependency_injection

import android.content.Context
import com.example.movieappmad24.data.MovieDatabase
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.view_models.MovieViewModelFactory

object InjectorUtils {

    private fun getMovieRepository(context: Context): MovieRepository {
        return MovieRepository.getInstance(MovieDatabase.getDatabase(context.applicationContext).movieDataAccessObject())
    }

    fun provideMovieViewModelFactory(context: Context): MovieViewModelFactory {
        val movieRepository = getMovieRepository(context)

        return MovieViewModelFactory(movieRepository = movieRepository)
    }
}