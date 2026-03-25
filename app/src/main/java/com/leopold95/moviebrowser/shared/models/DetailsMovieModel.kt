package com.leopold95.moviebrowser.shared.models

import kotlinx.serialization.Serializable

@Serializable
data class DetailsMovieModel(
    val id: Long,
    val title: String,
    val imagePath: String,
    val description: String,
    val geners: List<String>,
    val rating: Float = 0f,
    val releaseYear: String = "",
    val duration: Int = 0
)
