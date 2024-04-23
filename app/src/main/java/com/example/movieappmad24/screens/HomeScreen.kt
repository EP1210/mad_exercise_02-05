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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movieappmad24.dependency_injection.InjectorUtils
import com.example.movieappmad24.models.MovieWithImages
import com.example.movieappmad24.navigation.Screen
import com.example.movieappmad24.ui.theme.Red
import com.example.movieappmad24.view_models.HomeViewModel
import com.example.movieappmad24.view_models.WatchlistViewModel
import com.example.movieappmad24.widgets.SimpleBottomAppBar
import com.example.movieappmad24.widgets.SimpleEventIcon
import com.example.movieappmad24.widgets.SimpleTopAppBar

@Composable
fun HomeScreen(
    navigationController: NavController,
    route: String
) {
    val homeViewModel: HomeViewModel = viewModel(factory = InjectorUtils.provideMovieViewModelFactory(context = LocalContext.current))

    Scaffold(
        topBar = {
            SimpleTopAppBar(title = "Movie App")
        },
        bottomBar = {
            SimpleBottomAppBar(navigationController = navigationController, currentRoute = route)
        }
    ) {
        MovieList(
            movies = homeViewModel.allMovies.collectAsState().value,
            viewModel = homeViewModel,
            padding = it,
            navigationController = navigationController
        )
    }
}

@Composable
fun MovieRow(
    instance: MovieWithImages,
    onItemClick: (Long) -> Unit = {},
    onFavouriteClick: () -> Unit
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
                onItemClick(instance.movie.movieId)
            }
    ) {

        Column {
            Box {
                AsyncImage(
                    model = instance.movieImages[0],
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .aspectRatio(ratio = 18.5f / 9f)
                )
                SimpleEventIcon(
                    icon = when {
                        instance.movie.isFavourite -> Icons.Default.Favorite
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
                    text = instance.movie.title,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(start = 7.dp)
                        .weight(weight = 7f)
                )
                SimpleEventIcon(
                    icon = Icons.Default.KeyboardArrowUp,
                    modifier = Modifier
                        .weight(weight = 1f)
                        .rotate(degrees = arrowRotation)
                ) {
                    cardExpansion = !cardExpansion
                }
            }
            if (cardExpansion) {
                DisplayMovieDetails(instance = instance)
            }
        }
    }
}

@Composable
fun DisplayMovieDetails(
    instance: MovieWithImages
) {
    Column(
        modifier = Modifier
            .padding(start = 12.dp, end = 12.dp, top = 10.dp, bottom = 12.dp)
    ) {
        Text(
            text = """Director: ${instance.movie.director}
                        |Released: ${instance.movie.year}
                        |Genre: ${instance.movie.genre}
                        |Actors: ${instance.movie.actors}
                        |Rating: ${instance.movie.rating}
                    """.trimMargin()
        )
        HorizontalDivider(
            modifier = Modifier
                .padding(vertical = 5.dp)
        )
        Text(
            text = "Plot: ${instance.movie.plot}"
        )
    }
}

@Composable
fun MovieList(
    movies: List<MovieWithImages>,
    padding: PaddingValues,
    navigationController: NavController,
    viewModel: ViewModel
) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues = padding)
    ) {
        items(items = movies) { movie ->
            MovieRow(
                instance = movie,
                onItemClick = { movieId ->
                    navigationController.navigate(route = Screen.Detail.passMovieId(movieId = movieId))
                },
                onFavouriteClick = {
                    when (viewModel) { // todo: add or remove from favourites
                        is HomeViewModel -> viewModel.toggleIsFavouriteState(instance = movie)
                        is WatchlistViewModel -> viewModel.toggleIsFavouriteState(instance = movie)
                    }
                }
            )
        }
    }
}