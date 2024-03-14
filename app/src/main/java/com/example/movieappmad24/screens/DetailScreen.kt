package com.example.movieappmad24.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.ui.theme.Purple80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(movieId: String?, navigationController: NavController) {
    getMovies().forEach { movie ->
        if (movie.id == movieId) {
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = movie.title
                            )
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    navigationController.popBackStack()
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = null
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Purple80
                        )
                    )
                }
            ) {
                Column(
                    modifier = Modifier
                        .padding(paddingValues = it)
                ) {
                    MovieRow(movie = movie)
                    LazyRow {
                        items(items = movie.images.drop(n = 1)) { image ->
                            Card(
                                shape = RoundedCornerShape(size = 20.dp),
                                modifier = Modifier
                                    .padding(all = 5.dp)
                            ) {
                                AsyncImage(
                                    model = image,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}