package com.leopold95.moviebrowser.screens.favourites

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.leopold95.moviebrowser.R
import com.leopold95.moviebrowser.screens.favourites.components.FavouriteItem
import com.leopold95.moviebrowser.screens.favourites.viewmodels.FavouritesViewModel
import com.leopold95.moviebrowser.ui.ApplicationScreen
import com.leopold95.moviebrowser.ui.components.CenteredTextComponent

@Composable
fun FavouritesView(nav: NavHostController){
    val viewModel: FavouritesViewModel = hiltViewModel()
    val favorites by viewModel.favorites.collectAsStateWithLifecycle()

    if (favorites.isEmpty()) {
        CenteredTextComponent(stringResource(R.string.feature_favourites_empty))
        return
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = rememberLazyListState(),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
    ) {
        items(
            items = favorites,
            key = { it.id }
        ) {
            FavouriteItem(
                movie = it,
                onRemoveClick = viewModel::onRemoveClicked,
                onMovieClick = { movieId ->
                    nav.navigate(ApplicationScreen.Details(movieId = movieId))
                }
            )
        }
    }
}