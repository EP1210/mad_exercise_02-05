package com.example.movieappmad24.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movieappmad24.dependency_injection.InjectorUtils
import com.example.movieappmad24.models.MovieImage
import com.example.movieappmad24.view_models.DetailViewModel
import com.example.movieappmad24.widgets.SimpleEventIcon
import com.example.movieappmad24.widgets.SimpleTopAppBar

@Composable
fun DetailScreen(
    movieId: Long?,
    navigationController: NavController
) {
    val detailViewModel: DetailViewModel = viewModel(factory = InjectorUtils.provideMovieViewModelFactory(context = LocalContext.current))
    val instance = detailViewModel.searchMovieById(movieId = movieId)?.collectAsState()?.value

    if (instance != null) {
        Scaffold(
            topBar = {
                SimpleTopAppBar(
                    title = instance.movie.title
                ) {
                    SimpleEventIcon(
                        icon = Icons.AutoMirrored.Filled.ArrowBack
                    ) {
                        navigationController.popBackStack()
                    }
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(paddingValues = it)
                    .verticalScroll(state = rememberScrollState())
            ) {
                MovieRow(
                    instance = instance,
                    onFavouriteClick = {
                        detailViewModel.updateFavouriteState(instance = instance)
                    }
                )
                HorizontalDivider(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                )
                Text(
                    text = "Movie Trailer"
                )
                MovieTrailer(movieTrailer = instance.movie.trailer)
                HorizontalDivider(
                    modifier = Modifier
                        .padding(all = 5.dp)
                )
                MovieImageGallery(movieImages = instance.movieImages)
            }
        }
    }
}

@Composable
fun MovieImageGallery(
    movieImages: List<MovieImage>
) {
    LazyRow {
        items(items = movieImages.drop(n = 1)) { image ->
            Card(
                shape = RoundedCornerShape(size = 20.dp),
                modifier = Modifier
                    .padding(all = 15.dp)
                    .size(size = 300.dp)
            ) {
                AsyncImage(
                    model = image.url,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun MovieTrailer(
    movieTrailer: String
) {
    var lifecycle by remember {
        mutableStateOf(value = Lifecycle.Event.ON_CREATE)
    }
    val context = LocalContext.current
    val trailer = MediaItem.fromUri("android.resource://${context.packageName}/$movieTrailer")
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
    var videoPaused by remember {
        mutableStateOf(value = true)
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
                    if (!videoPaused) {
                        exoPlayer.play()
                    }
                }

                Lifecycle.Event.ON_STOP -> {
                    playerView.onPause()
                    videoPaused = !exoPlayer.isPlaying
                    exoPlayer.pause()
                }

                else -> Unit
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(ratio = 16f / 9f)
    )
}