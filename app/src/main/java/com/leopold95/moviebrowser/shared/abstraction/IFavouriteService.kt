package com.leopold95.moviebrowser.shared.abstraction

import com.leopold95.moviebrowser.shared.models.ShortMovieModel
import kotlinx.coroutines.flow.StateFlow

interface IFavouriteService {
    suspend fun add(movie: ShortMovieModel)
    suspend fun remove(movieId: Long)
    fun list(): List<ShortMovieModel>
    suspend fun load()
    suspend fun save()
    fun exists(id: Long): Boolean

    val favoritesFlow: StateFlow<List<ShortMovieModel>>
}