package com.leopold95.moviebrowser.shared.abstraction

import com.leopold95.moviebrowser.shared.RequestPromise
import com.leopold95.moviebrowser.shared.models.DetailsMovieModel
import com.leopold95.moviebrowser.shared.models.ShortMovieModel

interface IMovieService {
    suspend fun getList(): RequestPromise<List<ShortMovieModel>>
    suspend fun getDetails(id: Long): RequestPromise<DetailsMovieModel>
}