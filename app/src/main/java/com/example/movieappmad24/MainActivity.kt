package com.example.movieappmad24

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movieappmad24.ui.theme.MovieAppMAD24Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppMAD24Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Card {
                        Image(painter = painterResource(id = R.drawable.movie_image), contentDescription = "placeholder_image")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieRow() {
    Column {
            Card(
                shape = RectangleShape,
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Box {
                    Image(
                        painter = painterResource(id = R.drawable.movie_image),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(all = 5.dp)
                            .clip(shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    )
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        modifier = Modifier
                            .align(alignment = Alignment.TopEnd)
                            .padding(all = 20.dp)
                    )
                }
                Row {
                    Text(
                        text = "Avatar",
                        modifier = Modifier
                            .padding(start = 10.dp, bottom = 15.dp)
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = null,
                    )
                }
            }
    }
}