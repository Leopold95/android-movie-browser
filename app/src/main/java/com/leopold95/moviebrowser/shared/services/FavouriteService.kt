package com.leopold95.moviebrowser.shared.services

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.google.gson.Gson
import com.leopold95.moviebrowser.shared.abstraction.IFavouriteService
import com.leopold95.moviebrowser.shared.models.ShortMovieModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavouriteService @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : IFavouriteService {

    private val gson = Gson()
    private val favorites = mutableListOf<ShortMovieModel>()
    private val key = stringSetPreferencesKey("favorites_json")

    private val _favoritesFlow = MutableStateFlow<List<ShortMovieModel>>(emptyList())
    override val favoritesFlow: StateFlow<List<ShortMovieModel>> = _favoritesFlow.asStateFlow()

    override suspend fun add(movie: ShortMovieModel) {
        if (favorites.none { it.id == movie.id }) {
            favorites.add(movie)
            _favoritesFlow.value = favorites.toList()
        }
        save()
    }

    override suspend fun remove(movieId: Long) {
        favorites.removeAll { it.id == movieId }
        _favoritesFlow.value = favorites.toList()
        save()
    }

    override fun list(): List<ShortMovieModel> {
        return favorites.toList()
    }

    override suspend fun load() {
        val prefs = dataStore.data.first()
        val jsonSet = prefs[key] ?: emptySet()
        val loaded = jsonSet.mapNotNull { gson.fromJson(it, ShortMovieModel::class.java) }
        favorites.clear()
        favorites.addAll(loaded)
        _favoritesFlow.value = favorites.toList()
    }

    override suspend fun save() {
        val jsonSet = favorites.map { gson.toJson(it) }.toSet()
        dataStore.edit { prefs ->
            prefs[key] = jsonSet
        }
    }

    override fun exists(id: Long): Boolean {
        return favorites.any { it.id == id }
    }
}