package com.leopold95.moviebrowser.shared.modules

import com.leopold95.moviebrowser.shared.abstraction.IFavouriteService
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavouritesEntryPoints {
    fun getFavouriteService(): IFavouriteService
}