package com.example.movieappmad24.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieImage
import com.example.movieappmad24.models.MovieWithImages
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDataAccessObject {
    
    @Insert
    suspend fun insert(movie: Movie)

    @Insert
    suspend fun insertMovies(movies: List<Movie>)

    @Insert
    suspend fun insertMovieImages(movieImages: List<MovieImage>)

    @Update
    suspend fun update(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)

    @Transaction
    @Query("select * from Movie")
    fun queryAllMovies(): Flow<List<MovieWithImages>>

    @Transaction
    @Query("select * from Movie where isFavourite = 1") // in SQLite 0 is false and 1 is true
    fun queryFavouriteMovies(): Flow<List<MovieWithImages>>

    @Transaction
    @Query("select * from Movie where movieId = :movieId")
    suspend fun queryMovieById(movieId: Long?): MovieWithImages?

    @Query("select movieId from Movie")
    suspend fun queryMovieIds(): List<Long>
}