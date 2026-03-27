package com.leopold95.moviebrowser.shared.services

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.google.gson.Gson
import com.leopold95.moviebrowser.shared.abstraction.IFavouriteService
import com.leopold95.moviebrowser.shared.models.ShortMovieModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavouriteService @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : IFavouriteService {

    private val gson = Gson()
    private val key = stringSetPreferencesKey("favorites_json")

    override val favouritesFlow: Flow<List<ShortMovieModel>> = dataStore.data.map { prefs ->
        val jsonSet = prefs[key] ?: emptySet()
       jsonSet.mapNotNull { json -> gson.fromJson(json, ShortMovieModel::class.java) }
    }

    override suspend fun add(movie: ShortMovieModel) {
        dataStore.edit { prefs ->
            val list = readList(prefs).toMutableList()
            if (list.none { it.id == movie.id }) {
                list.add(movie)
                writeList(prefs, list)
            }
        }
    }

    override suspend fun remove(movieId: Long) {
        dataStore.edit { prefs ->
            val list = readList(prefs).filterNot { it.id == movieId }
            writeList(prefs, list)
        }
    }

    override suspend fun exists(id: Long): Boolean {
        return favouritesFlow.first().any { it.id == id }
    }

    private fun readList(prefs: Preferences): List<ShortMovieModel> {
        val jsonSet = prefs[key].orEmpty()
        return jsonSet.mapNotNull { json ->
            try {
                gson.fromJson(json, ShortMovieModel::class.java)
            } catch (_: Exception) {
                null
            }
        }
    }

    private fun writeList(prefs: MutablePreferences, list: List<ShortMovieModel>) {
        prefs[key] = list.map { movie -> gson.toJson(movie) }.toSet()
    }

}