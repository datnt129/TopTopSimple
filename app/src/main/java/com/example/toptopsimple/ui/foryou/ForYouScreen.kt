package com.example.toptopsimple.ui.foryou

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.toptopsimple.ui.theme.TopTopSimpleTheme
import com.example.toptopsimple.ui.video.VideoDetailScreen
import com.example.toptopsimple.ui.video.VideoDetailViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ForYouScreen(
    modifier: Modifier = Modifier
) {
    val pageState = rememberPagerState (
        pageCount = {
            10
        }
    )
    VerticalPager(state = pageState) {videoId ->
        if (pageState.currentPage == videoId) {
            val viewModel: VideoDetailViewModel = hiltViewModel(key = videoId.toString())
            VideoDetailScreen(videoId = videoId, viewModel = viewModel)
        }
    }
}

@Preview
@Composable
fun ForYouScreenPreview() {
    TopTopSimpleTheme {
        ForYouScreen()
    }
}