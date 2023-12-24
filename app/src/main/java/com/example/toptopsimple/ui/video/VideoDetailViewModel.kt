package com.example.toptopsimple.ui.video

import android.util.Log
import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.RawResourceDataSource
import androidx.media3.exoplayer.ExoPlayer
import com.example.toptopsimple.data.repositories.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class VideoDetailViewModel @Inject constructor(
    val videoPlayer: ExoPlayer,
    val videoRepository: VideoRepository,
) : ViewModel() {
    private var _uiState = MutableStateFlow<VideoDetailUiState>(VideoDetailUiState.Default)
    val uiState: StateFlow<VideoDetailUiState>
        get() = _uiState
    init {
        Log.d("DevLog", "Video Detail VM Init")
        videoPlayer.repeatMode = Player.REPEAT_MODE_ALL
        videoPlayer.playWhenReady = true
        videoPlayer.prepare()
    }

    fun handleAction(action: VideoDetailAction) {
        when(action) {
            is VideoDetailAction.LoadData -> {
                val videoId = action.id
                loadVideo(videoId)
            }
            is VideoDetailAction.ToggleVideo -> {
                toggleVideo()
            }
        }
    }

    private fun loadVideo(videoId: Int) {
        _uiState.value = VideoDetailUiState.Loading
        viewModelScope.launch {
            val video = videoRepository.getVideo()
            playVideo(video)
            _uiState.value = VideoDetailUiState.Success
        }
    }


    @OptIn(UnstableApi::class)
    private fun playVideo(videoResourceId: Int) {
        val uri = RawResourceDataSource.buildRawResourceUri(videoResourceId)
        val mediaItem = MediaItem.fromUri(uri)
        videoPlayer.setMediaItem(mediaItem)
        videoPlayer.play()
    }

    private fun toggleVideo() {
        if(videoPlayer.isLoading) {

        } else if (videoPlayer.isPlaying) {
            videoPlayer.pause()
        } else {
            videoPlayer.play()
        }
    }

    override fun onCleared() {
        super.onCleared()
        videoPlayer.release()
    }
}

sealed interface VideoDetailUiState {
    data object Default: VideoDetailUiState
    data object Loading: VideoDetailUiState
    data object Success: VideoDetailUiState
    data class Error(val msg: String): VideoDetailUiState
}

sealed class VideoDetailAction {
    data class LoadData(val id: Int): VideoDetailAction()
    data object ToggleVideo: VideoDetailAction()
}