package com.leopold95.moviebrowser.screens.favourites.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leopold95.moviebrowser.shared.abstraction.IFavouriteService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val favouriteService: IFavouriteService
) : ViewModel() {
//    val favorites = favouriteService.favoritesFlow
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5000),
//            initialValue = emptyList()
//        )

    val favourites = favouriteService.favouritesFlow

    fun onRemoveClicked(id: Long) = viewModelScope.launch {
        favouriteService.remove(id)
    }
}