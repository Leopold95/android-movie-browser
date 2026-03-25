package com.leopold95.moviebrowser.screens.details.state

import com.leopold95.moviebrowser.shared.models.DetailsMovieModel

data class DetailsUiState(
    val movie: DetailsMovieModel? = null,
    val isFavourite: Boolean = false,

    val isLoading: Boolean = true,
    val isError: Boolean = false
)