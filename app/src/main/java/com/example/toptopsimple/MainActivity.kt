package com.example.toptopsimple

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.toptopsimple.ui.following.FollowingScreen
import com.example.toptopsimple.ui.foryou.ForYouScreen
import com.example.toptopsimple.ui.theme.TopTopSimpleTheme
import com.example.toptopsimple.ui.video.VideoDetailScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TopTopSimpleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    VideoDetailScreen(videoId = 1)
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DemoPager() {
    val pageState = rememberPagerState(
        pageCount = { 10 }
    )

    val colors = listOf(Color.Blue, Color.Gray, Color.Green)

    VerticalPager(state = pageState) { page ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colors.random()),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Page $page",
                style = MaterialTheme.typography.displayMedium.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )
        }

    }
}

@Preview
@Composable
fun DemoPagerPreview() {
    TopTopSimpleTheme {
        DemoPager()
    }
}