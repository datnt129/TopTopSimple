package com.example.toptopsimple.ui.following

import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.RawResourceDataSource
import androidx.media3.exoplayer.ExoPlayer
import com.example.toptopsimple.R
import com.example.toptopsimple.designsystem.TopTopVideoPlayer
import com.example.toptopsimple.ui.theme.TopTopSimpleTheme

@Composable
fun FollowingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        CreatorCard(name = "Some One", accId = "@someone", onFollow = { /*TODO*/ }) {
            
        }
    }
}

@OptIn(UnstableApi::class) @Composable
fun CreatorCard(
    modifier: Modifier = Modifier,
    name: String,
    accId: String,
    onFollow: () -> Unit,
    onClose: () -> Unit
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(16.dp))
    ) {
        val videoPlayer = ExoPlayer.Builder(LocalContext.current).build()
        videoPlayer.repeatMode = Player.REPEAT_MODE_ALL
        videoPlayer.playWhenReady = true
        videoPlayer.prepare()

        val (videoIntro, btnClose, imgAvatar, tvName, tvAccId, btnFollow) = createRefs()

        TopTopVideoPlayer(player = videoPlayer, modifier = Modifier.constrainAs(videoIntro) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        })

        IconButton(onClick = { onClose }, modifier = Modifier
            .constrainAs(btnClose) {
                top.linkTo(parent.top, margin = 12.dp)
                end.linkTo(parent.end, margin = 12.dp)
            }
            .size(16.dp)) {
            Icon(Icons.Default.Close, contentDescription = "Close Button", tint = Color.White)
        }

        Button(
            onClick = onFollow,
            modifier = Modifier
                .constrainAs(btnFollow) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom, margin = 24.dp)
                }
                .padding(
                    horizontal = 48.dp,
                    vertical = 12.dp
                ),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Follow",
                style = MaterialTheme.typography.titleMedium.copy(color = Color.White)
            )
        }

        Text(
            text = accId,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.constrainAs(tvAccId) {
                bottom.linkTo(btnFollow.top, margin = 8.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.constrainAs(tvName) {
                bottom.linkTo(tvAccId.top, margin = 8.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        AvatarFollowing(modifier = Modifier.constrainAs(imgAvatar) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(tvName.top, margin = 8.dp)
        })

        val uri = RawResourceDataSource.buildRawResourceUri(R.raw.video1)
        val mediaItem = MediaItem.fromUri(uri)
        videoPlayer.setMediaItem(mediaItem)

        SideEffect {
            videoPlayer.play()
        }
    }

}

@Composable
fun AvatarFollowing(modifier: Modifier = Modifier) {
    val size = LocalConfiguration.current.screenWidthDp * 0.2;
    Image(
        painter = painterResource(id = R.drawable.ic_dog),
        contentDescription = "Avatar",
        modifier = modifier
            .size(size.dp)
            .background(color = Color.White, shape = CircleShape)
            .border(
                color = Color.White,
                width = 2.dp,
                shape = CircleShape
            )
            .clip(CircleShape)
    )
}

@Preview
@Composable
fun AvatarFollowingPreview() {
    TopTopSimpleTheme {
        AvatarFollowing()
    }
}