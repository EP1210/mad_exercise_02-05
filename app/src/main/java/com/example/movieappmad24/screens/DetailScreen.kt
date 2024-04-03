package com.example.movieappmad24.screens

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.view_models.MovieViewModel
import com.example.movieappmad24.widgets.SimpleTopAppBar

@Composable
fun DetailScreen(
    movieId: String?,
    navigationController: NavController,
    viewModel: MovieViewModel
) {
    val movie = viewModel.getMovieById(movieId = movieId)

    if (movie != null) {
        Scaffold(
            topBar = {
                SimpleTopAppBar(
                    title = movie.title,
                    navigationController = navigationController
                )
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues = it)
            ) {
                items(count = 1) {
                    MovieRow(
                        movie = movie,
                        onFavouriteClick = {
                            viewModel.toggleIsFavouriteState(movie = movie)
                            viewModel.addToRemoveFromFavourites(movie = movie)
                        }
                    )
                    Divider(
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                    )
                    Text(
                        text = "Movie Trailer"
                    )
                    MovieTrailer(movie = movie)
                    Divider(
                        modifier = Modifier
                            .padding(all = 5.dp)
                    )
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
                    .padding(all = 15.dp)
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

@Composable
fun MovieTrailer(movie: Movie) {
    var lifecycle by remember {
        mutableStateOf(value = Lifecycle.Event.ON_CREATE)
    }
    val context = LocalContext.current
    val trailer = MediaItem.fromUri("android.resource://${context.packageName}/${movie.trailer}")
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(trailer)
            prepare()
        }
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer = observer)

        onDispose {
            exoPlayer.release()
            lifecycleOwner.lifecycle.removeObserver(observer = observer)
        }
    }

    AndroidView(
        factory = {
            PlayerView(context).also { playerView ->
                playerView.player = exoPlayer
            }
        },
        update = { playerView ->
            when (lifecycle) {
                Lifecycle.Event.ON_RESUME -> {
                    playerView.onResume()
                    if (exoPlayer.currentPosition != 0L) {
                        exoPlayer.playWhenReady = true
                    }
                }
                Lifecycle.Event.ON_STOP -> {
                    playerView.onPause()
                    exoPlayer.playWhenReady = false
                }
                else -> Unit
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(ratio = 16f / 9f)
    )
}