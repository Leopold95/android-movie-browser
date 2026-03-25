package com.leopold95.moviebrowser.ui

import kotlinx.serialization.Serializable

@Serializable
sealed class ApplicationScreen {
    @Serializable
    data object Home: ApplicationScreen()

    @Serializable
    data class Details(val movieId: Long): ApplicationScreen()

    @Serializable
    data object Favourites: ApplicationScreen()
}