package com.example.movieappmad24.data

import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieWithImages
import kotlinx.coroutines.flow.Flow

class MovieRepository(private val movieDataAccessObject: MovieDataAccessObject) {

    suspend fun insertMovie(movie: Movie) = movieDataAccessObject.insert(movie = movie)

    suspend fun updateMovie(movie: Movie) = movieDataAccessObject.update(movie = movie)

    suspend fun deleteMovie(movie: Movie) = movieDataAccessObject.delete(movie = movie)

    fun getAllMovies(): Flow<List<MovieWithImages>> = movieDataAccessObject.queryAllMovies()

    fun getFavouriteMovies(): Flow<List<MovieWithImages>> = movieDataAccessObject.queryFavouriteMovies()

    fun getMovieById(movieId: Long?): MovieWithImages? = movieDataAccessObject.queryMovieById(movieId = movieId)

    companion object {
        @Volatile
        private var Instance: MovieRepository? = null

        fun getInstance(movieDataAccessObject: MovieDataAccessObject) = Instance ?: synchronized(this) {
            Instance ?: MovieRepository(movieDataAccessObject).also {
                Instance = it
            }
        }
    }
}