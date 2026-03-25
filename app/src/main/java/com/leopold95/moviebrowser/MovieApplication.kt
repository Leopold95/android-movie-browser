package com.leopold95.moviebrowser

import android.app.Application
import com.leopold95.moviebrowser.shared.modules.FavouritesEntryPoints
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@HiltAndroidApp
class MovieApplication : Application() {
    private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()

        val ep = EntryPointAccessors.fromApplication(this, FavouritesEntryPoints::class.java)

        appScope.launch {
            ep.getFavouriteService().load()
        }
    }
}