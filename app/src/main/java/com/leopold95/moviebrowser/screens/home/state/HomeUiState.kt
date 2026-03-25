package com.leopold95.moviebrowser.screens.home.state

import androidx.compose.runtime.Stable
import com.leopold95.moviebrowser.shared.models.ShortMovieModel

@Stable
data class HomeUiState(
    val movies: List<ShortMovieModel> = emptyList(),

    val isLoading: Boolean = true,
    val isError: Boolean = false,
)
