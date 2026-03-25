package com.leopold95.moviebrowser.screens.favourites.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leopold95.moviebrowser.shared.abstraction.IFavouriteService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val favouriteService: IFavouriteService
) : ViewModel() {
    val favorites = favouriteService.favoritesFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onRemoveClicked(id: Long) = viewModelScope.launch {
        favouriteService.remove(id)
    }
}