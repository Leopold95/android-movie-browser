package com.leopold95.moviebrowser.screens.details.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leopold95.moviebrowser.screens.details.state.DetailsUiState
import com.leopold95.moviebrowser.shared.abstraction.IFavouriteService
import com.leopold95.moviebrowser.shared.abstraction.IMovieService
import com.leopold95.moviebrowser.shared.models.ShortMovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject  constructor(
    private val savedStateHandle: SavedStateHandle,
    private val favouriteService: IFavouriteService,
    private val movieService: IMovieService
): ViewModel() {
    private val movieId: Long = savedStateHandle.get<Long>("movieId") ?: 0L

    private val _state = MutableStateFlow(DetailsUiState())
    val state = _state.asStateFlow()

    fun favouriteClicked() = viewModelScope.launch {
        if (favouriteService.exists(movieId)) {
            favouriteService.remove(movieId)
            _state.update { it.copy(isFavourite = false) }
            return@launch
        }

        val movie = _state.value.movie ?: return@launch
        favouriteService.add(
            ShortMovieModel(
                name = movie.title,
                id = movie.id,
                imageUrl = movie.imagePath,
                description = movie.description
            )
        )
        _state.update { it.copy(isFavourite = true) }
    }

    init {
        load()
    }

    private fun load() = viewModelScope.launch {
        val response = movieService.getDetails(movieId)
        if (!response.success){
            _state.update { it.copy(isLoading = false, isError = true) }
            return@launch
        }

        if (favouriteService.exists(movieId)) {
            _state.update { it.copy(isFavourite = true) }
        }

        _state.update { it.copy(movie = response.data, isLoading = false) }
    }
}