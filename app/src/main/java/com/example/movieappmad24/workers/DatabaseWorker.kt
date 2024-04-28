package com.example.movieappmad24.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.movieappmad24.data.MovieDatabase.Companion.getDatabase
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.models.MovieImage
import com.example.movieappmad24.models.getMovies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

private const val TAG = "DatabaseWorker"

class DatabaseWorker(context: Context, parameters: WorkerParameters) : CoroutineWorker(context, parameters) {

    private val dataAccessObject = getDatabase(context = context).movieDataAccessObject()
    private val movieRepository = MovieRepository(movieDataAccessObject = dataAccessObject)

    override suspend fun doWork(): Result {
        return coroutineScope {
            return@coroutineScope withContext(Dispatchers.Main) {
                return@withContext try {
                    movieRepository.insertAllMovies(movies = getMovies())
                    val movieIds = movieRepository.getMovieIds()
                    val movieImages = mutableListOf<MovieImage>()

                    for (index in 0 until getMovies().size) {
                        for (url in getMovies()[index].images) {
                            movieImages.add(element = MovieImage(movieId = movieIds[index], url = url))
                        }
                    }
                    movieRepository.insertAllMovieImages(movieImages = movieImages)
                    Result.success()
                } catch (throwable: Throwable) {
                    Log.e(TAG, "An error occurred while seeding the database!", throwable)
                    Result.failure()
                }
            }
        }
    }
}