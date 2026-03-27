package com.leopold95.moviebrowser.screens.home.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leopold95.moviebrowser.screens.home.state.HomeUiState
import com.leopold95.moviebrowser.shared.abstraction.IFavouriteService
import com.leopold95.moviebrowser.shared.abstraction.IMovieService
import com.leopold95.moviebrowser.shared.models.ShortMovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val movieService: IMovieService,
    val favouriteService: IFavouriteService
): ViewModel() {
    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

    val favouriteIds: StateFlow<Set<Long>> = favouriteService.favouritesFlow
        .map { list -> list.map { it.id }.toSet() }
        .distinctUntilChanged()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    init {
        viewModelScope.launch { loadMovies() }
    }

    fun onFavouriteClicked(model: ShortMovieModel) = viewModelScope.launch {
        if (model.id in favouriteIds.value) {
            favouriteService.remove(model.id)
        } else {
            favouriteService.add(model)
        }
    }

    private suspend fun loadMovies(){
        val result = movieService.getList()
        if (!result.success){
            _state.update { it.copy(isLoading = false, isError = true) }
            return
        }

        _state.update {
            it.copy(
                isLoading = false,
                isError = false,
                movies = result.data.orEmpty()
            )
        }
    }
}