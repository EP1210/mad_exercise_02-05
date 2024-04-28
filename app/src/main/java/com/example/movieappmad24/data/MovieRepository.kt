package com.example.movieappmad24.data

import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieImage
import com.example.movieappmad24.models.MovieWithImages
import kotlinx.coroutines.flow.Flow

class MovieRepository(private val movieDataAccessObject: MovieDataAccessObject) {

    suspend fun insertMovie(movie: Movie) = movieDataAccessObject.insert(movie = movie)

    suspend fun insertAllMovies(movies: List<Movie>) = movieDataAccessObject.insertMovies(movies = movies)

    suspend fun insertAllMovieImages(movieImages: List<MovieImage>) = movieDataAccessObject.insertMovieImages(movieImages = movieImages)

    suspend fun updateMovie(movie: Movie) = movieDataAccessObject.update(movie = movie)

    suspend fun deleteMovie(movie: Movie) = movieDataAccessObject.delete(movie = movie)

    fun getAllMovies(): Flow<List<MovieWithImages>> = movieDataAccessObject.queryAllMovies()

    fun getFavouriteMovies(): Flow<List<MovieWithImages>> = movieDataAccessObject.queryFavouriteMovies()

    suspend fun getMovieById(movieId: Long?): MovieWithImages? = movieDataAccessObject.queryMovieById(movieId = movieId)

    suspend fun getMovieIds(): List<Long> = movieDataAccessObject.queryMovieIds()

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