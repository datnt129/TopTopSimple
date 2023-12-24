package com.example.toptopsimple.ui.video

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.Player
import com.example.toptopsimple.designsystem.TopTopVideoPlayer
import com.example.toptopsimple.ui.video.composables.SideBarView
import com.example.toptopsimple.ui.video.composables.VideoInfoArea
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun VideoDetailScreen(
    videoId: Int,
    viewModel: VideoDetailViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    if (uiState.value == VideoDetailUiState.Default) {
        viewModel.handleAction(VideoDetailAction.LoadData(videoId))
    }

    VideoDetailScreen(uiState = uiState.value, player = viewModel.videoPlayer) { action ->
        viewModel.handleAction(action = action)
    }
}

@Composable
fun VideoDetailScreen(
    uiState: VideoDetailUiState,
    player: Player,
    handleAction: (VideoDetailAction) -> Unit
) {
    when (uiState) {
        is VideoDetailUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Loading")
            }
        }

        is VideoDetailUiState.Success -> {
            VideoDetailScreen(player = player, handleAction = handleAction)
        }

        else -> {

        }
    }
}

@Composable
fun VideoDetailScreen(
    player: Player,
    handleAction: (VideoDetailAction) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                onClick = {
                    handleAction(VideoDetailAction.ToggleVideo)
                }
            )
    ) {
        val (videoPlayer, sideBar, videoInfo) = createRefs()

        TopTopVideoPlayer(player = player, modifier = Modifier.constrainAs(videoPlayer) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.matchParent
            height = Dimension.matchParent
        })

        SideBarView(
            onAvatarClick = { /*TODO*/ },
            onLikeClick = { /*TODO*/ },
            onCmtClick = { /*TODO*/ },
            onSaveClick = { /*TODO*/ },
            onShareClick = {},
            modifier = Modifier.constrainAs(sideBar) {
                end.linkTo(parent.end, margin = 16.dp)
                bottom.linkTo(parent.bottom, margin = 16.dp)
            }
        )

        VideoInfoArea(
            accountName = "Top Top",
            videoName = "My Video",
            hashTags = listOf("#Android", "#Compose"),
            songName = "My Music",
            modifier = Modifier.constrainAs(videoInfo) {
                start.linkTo(parent.start, margin = 16.dp)
                bottom.linkTo(sideBar.bottom)
                end.linkTo(sideBar.start)
                width = Dimension.fillToConstraints
            }
        )
    }
}