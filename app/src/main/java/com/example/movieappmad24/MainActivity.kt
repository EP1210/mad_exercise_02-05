package com.example.movieappmad24

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale.Companion.FillWidth
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getBottomNavigationItems
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.ui.theme.MovieAppMAD24Theme
import com.example.movieappmad24.ui.theme.Purple80

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppMAD24Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    ShowScreen(displayedMovies = getMovies())
                }
            }
        }
    }
}

@Composable
fun MovieRow(
    movie: Movie
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
            .fillMaxWidth()
            .animateContentSize()
    ) {

        Column {
            Box {
                AsyncImage(
                    model = movie.images[0],
                    contentDescription = null,
                    contentScale = FillWidth,
                    modifier = Modifier
                        .aspectRatio(ratio = 18.5f / 9f)
                )
                IconButton(
                    onClick = {
                        // TODO: Add to favourites logic
                    },
                    modifier = Modifier
                        .align(alignment = Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = null
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
    padding: PaddingValues
) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues = padding)
    ) {
        items(items = movies) { movie ->
            MovieRow(movie = movie)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowScreen(
    displayedMovies: List<Movie>
) {
    var bottomItemIndex by rememberSaveable {
        mutableIntStateOf(value = 0)
    }

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
                getBottomNavigationItems().forEachIndexed { clickedIndex, navigationItem ->
                    NavigationBarItem(
                        selected = bottomItemIndex == clickedIndex, // a specific item is selected if its index matches the clicked index
                        onClick = {
                            bottomItemIndex = clickedIndex
                            // TODO: Navigation logic
                        },
                        icon = {
                            Icon(
                                imageVector = when (bottomItemIndex) {
                                    clickedIndex -> navigationItem.selected
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
        MovieList(movies = displayedMovies, padding = it)
    }
}