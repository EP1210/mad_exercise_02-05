package com.example.movieappmad24.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.widgets.SimpleTopAppBar

@Composable
fun DetailScreen(
    movieId: String?,
    navigationController: NavController,
    route: String
) {
    getMovies().forEach { movie ->
        if (movie.id == movieId) {
            Scaffold(
                topBar = {
                    SimpleTopAppBar(movie = movie, navigationController = navigationController, currentRoute = route)
                }
            ) {
                Column(
                    modifier = Modifier
                        .padding(paddingValues = it)
                ) {
                    MovieRow(movie = movie)
                    MovieImageGallery(movie = movie)
                }
            }
        }
    }
}

@Composable
fun MovieImageGallery(
    movie: Movie
) {
    LazyRow {
        items(items = movie.images.drop(n = 1)) { image ->
            Card(
                shape = RoundedCornerShape(size = 20.dp),
                modifier = Modifier
                    .padding(all = 5.dp)
                    .size(size = 300.dp)
            ) {
                AsyncImage(
                    model = image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}