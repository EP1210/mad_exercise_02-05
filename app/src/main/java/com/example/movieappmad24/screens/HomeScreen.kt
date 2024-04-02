package com.example.movieappmad24.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.navigation.Screen
import com.example.movieappmad24.ui.theme.Red
import com.example.movieappmad24.view_models.MovieViewModel
import com.example.movieappmad24.widgets.SimpleBottomAppBar
import com.example.movieappmad24.widgets.SimpleEventIcon
import com.example.movieappmad24.widgets.SimpleTopAppBar

@Composable
fun HomeScreen(
    navigationController: NavController,
    route: String,
    viewModel: MovieViewModel
) {
    Scaffold(
        topBar = {
            SimpleTopAppBar(title = "Movie App")
        },
        bottomBar = {
            SimpleBottomAppBar(navigationController = navigationController, currentRoute = route)
        }
    ) {
        MovieList(
            movies = viewModel.movies,
            viewModel = viewModel,
            padding = it,
            navigationController = navigationController
        )
    }
}

@Composable
fun MovieRow(
    movie: Movie,
    onItemClick: (String) -> Unit = {},
    onFavouriteClick: () -> Unit,
) {
    var cardExpansion by remember {
        mutableStateOf(value = false)
    }
    val arrowRotation by animateFloatAsState(
        targetValue = when (cardExpansion) {
            true -> 180f
            false -> 0f
        }
    )

    Card(
        shape = RoundedCornerShape(size = 20.dp),
        modifier = Modifier
            .padding(all = 10.dp)
            .animateContentSize()
            .clickable {
                onItemClick(movie.id)
            }
    ) {

        Column {
            Box {
                AsyncImage(
                    model = movie.images[0],
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .aspectRatio(ratio = 18.5f / 9f)
                )
                SimpleEventIcon(
                    icon = when {
                        movie.isFavourite -> Icons.Default.Favorite
                        else -> Icons.Default.FavoriteBorder
                    },
                    color = Red,
                    modifier = Modifier
                        .align(alignment = Alignment.TopEnd)
                ) {
                    onFavouriteClick()
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = movie.title,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(start = 7.dp)
                        .weight(weight = 7f) // text takes seven parts of row
                )
                SimpleEventIcon(
                    icon = Icons.Default.KeyboardArrowUp,
                    modifier = Modifier
                        .weight(weight = 1f) // icon button takes one part of row
                        .rotate(degrees = arrowRotation)
                ) {
                    cardExpansion = !cardExpansion
                }
            }
            if (cardExpansion) {
                DisplayMovieDetails(movie = movie)
            }
        }
    }
}

@Composable
fun DisplayMovieDetails(
    movie: Movie
) {
    Column(
        modifier = Modifier
            .padding(start = 12.dp, end = 12.dp, top = 10.dp, bottom = 12.dp)
    ) {
        Text(
            text = """Director: ${movie.director}
                        |Released: ${movie.year}
                        |Genre: ${movie.genre}
                        |Actors: ${movie.actors}
                        |Rating: ${movie.rating}
                    """.trimMargin()
        )
        Divider(
            modifier = Modifier
                .padding(vertical = 5.dp)
        )
        Text(
            text = "Plot: ${movie.plot}"
        )
    }
}

@Composable
fun MovieList(
    movies: List<Movie>,
    padding: PaddingValues,
    navigationController: NavController,
    viewModel: MovieViewModel
) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues = padding)
    ) {
        items(items = movies) { movie ->
            MovieRow(
                movie = movie,
                onItemClick = { movieId ->
                    navigationController.navigate(route = Screen.Detail.passMovieId(movieId = movieId))
                },
                onFavouriteClick = {
                    viewModel.toggleIsFavouriteState(movie = movie)
                    viewModel.addToRemoveFromFavourites(movie = movie)
                }
            )
        }
    }
}