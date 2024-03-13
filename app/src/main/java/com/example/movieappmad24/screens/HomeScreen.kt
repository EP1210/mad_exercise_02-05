package com.example.movieappmad24.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import com.example.movieappmad24.models.addToOrRemoveFromWatchlist
import com.example.movieappmad24.models.getBottomNavigationItems
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.models.watchlistContains
import com.example.movieappmad24.ui.theme.Purple80
import com.example.movieappmad24.ui.theme.Red

@Composable
fun HomeScreen(navigationController: NavController, route: String) {
    ShowScreen(displayedMovies = getMovies(), navigationController = navigationController, currentRoute = route)
}

@Composable
fun MovieRow(
    movie: Movie,
    onItemClick: (String) -> Unit = {}
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
            .padding(all = 5.dp)
            .fillMaxWidth()
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
                IconButton(
                    onClick = {
                        addToOrRemoveFromWatchlist(movie = movie)
                    },
                    modifier = Modifier
                        .align(alignment = Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = when {
                            watchlistContains(movie = movie) -> Icons.Default.Favorite
                            else -> Icons.Default.FavoriteBorder
                        },
                        contentDescription = null,
                        tint = Red
                    )
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
                IconButton(
                    onClick = {
                        cardExpansion = !cardExpansion
                    },
                    modifier = Modifier
                        .weight(weight = 1f) // icon button takes one part of row
                        .rotate(degrees = arrowRotation)
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = null
                    )
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
            .padding(start = 12.dp, top = 10.dp, bottom = 12.dp)
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
                .padding(top = 5.dp, bottom = 5.dp, end = 12.dp)
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
    navigationController: NavController
) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues = padding)
    ) {
        items(items = movies) { movie ->
            MovieRow(movie = movie) { movieId ->
                navigationController.navigate(route = "${Screen.Detail.route}/$movieId")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowScreen(
    navigationController: NavController,
    currentRoute: String,
    displayedMovies: List<Movie>
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Movie App"
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Purple80
                )
            )
        },
        bottomBar = {
            NavigationBar {
                getBottomNavigationItems().forEach { navigationItem ->
                    NavigationBarItem(
                        selected = navigationItem.route == currentRoute,
                        onClick = {
                            navigationController.navigate(route = navigationItem.route)
                        },
                        icon = {
                            Icon(
                                imageVector = when (navigationItem.route) {
                                    currentRoute -> navigationItem.selected
                                    else -> navigationItem.unselected
                                },
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(
                                text = navigationItem.label
                            )
                        }
                    )
                }
            }
        }
    ) {
        MovieList(movies = displayedMovies, padding = it, navigationController = navigationController)
    }
}