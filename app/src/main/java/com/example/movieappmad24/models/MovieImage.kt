package com.example.movieappmad24.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieImage(
    @PrimaryKey(autoGenerate = true) val imageId: Long = 0,
    val movieId: Long, // reference to the primary key of Movie
    val url: String
)