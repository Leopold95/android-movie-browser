package com.leopold95.moviebrowser.shared.abstraction

import com.leopold95.moviebrowser.shared.models.ShortMovieModel
import kotlinx.coroutines.flow.Flow

interface IFavouriteService {
    val favouritesFlow: Flow<List<ShortMovieModel>>
    suspend fun add(movie: ShortMovieModel)
    suspend fun remove(movieId: Long)
    suspend fun exists(id: Long): Boolean

}