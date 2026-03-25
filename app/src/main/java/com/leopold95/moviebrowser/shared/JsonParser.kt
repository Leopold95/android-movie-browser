package com.leopold95.moviebrowser.shared

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JsonParser @Inject constructor(){
    val gson = Gson()

    inline fun <reified T> format(json: String): T {
        return gson.fromJson(json, object : TypeToken<T>() {}.type)
    }
}