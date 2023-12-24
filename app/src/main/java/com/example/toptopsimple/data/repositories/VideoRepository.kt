package com.example.toptopsimple.data.repositories

import com.example.toptopsimple.R
import javax.inject.Inject

class VideoRepository @Inject constructor() {
    private val videos = listOf(
        R.raw.video1,
        R.raw.video2,
        R.raw.video3
    )

    fun getVideo() = videos.random()
}