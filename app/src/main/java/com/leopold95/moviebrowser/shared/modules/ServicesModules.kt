package com.leopold95.moviebrowser.shared.modules

import com.leopold95.moviebrowser.shared.abstraction.IFavouriteService
import com.leopold95.moviebrowser.shared.abstraction.IMovieService
import com.leopold95.moviebrowser.shared.services.FavouriteService
import com.leopold95.moviebrowser.shared.services.MovieService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServicesModules{
    @Binds
    abstract fun provideMovieService(impl: MovieService): IMovieService

    @Binds
    abstract fun provideFavouriteService(impl: FavouriteService): IFavouriteService
}