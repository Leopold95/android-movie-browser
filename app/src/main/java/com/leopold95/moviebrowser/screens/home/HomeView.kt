package com.leopold95.moviebrowser.screens.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.leopold95.moviebrowser.R
import com.leopold95.moviebrowser.screens.home.components.MovieListItem
import com.leopold95.moviebrowser.screens.home.viewmodels.HomeViewModel
import com.leopold95.moviebrowser.ui.ApplicationScreen
import com.leopold95.moviebrowser.ui.components.CenteredTextComponent
import com.leopold95.moviebrowser.ui.components.LoadingComponent

@Composable
fun HomeView(nav: NavHostController){
    val viewModel: HomeViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val favouriteIds by viewModel.favouriteIds.collectAsStateWithLifecycle(initialValue = emptySet<Long>())

    if (state.isLoading){
        LoadingComponent()
        return
    }

    if (state.isError){
        CenteredTextComponent(stringResource(R.string.feature_home_loading_error))
        return
    }

    if (state.movies.isEmpty()){
        CenteredTextComponent(stringResource(R.string.feature_home_no_movies))
        return
    }

    LazyColumn {
        items(
            items = state.movies,
            key = { it.id }
        ){ movie ->
            MovieListItem(
                model = movie,
                isFavourite = favouriteIds.contains(movie.id),
                onFavouriteClick = viewModel::onFavouriteClicked,
                onDetailsClicked = {
                    nav.navigate(ApplicationScreen.Details(movieId = movie.id))
                }
            )
        }
    }
}