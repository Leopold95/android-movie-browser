package com.leopold95.moviebrowser.screens.favourites.state

import com.leopold95.moviebrowser.shared.models.ShortMovieModel
import java.util.Collections.emptyList

data class FavouriteUiState(
    val list: List<ShortMovieModel> = emptyList()
)
