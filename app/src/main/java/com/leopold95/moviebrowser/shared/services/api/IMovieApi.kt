package com.leopold95.moviebrowser.shared.services.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IMovieApi {
    @GET("3/movie/popular")
    suspend fun getList(@Query("page") page: Int): Response<String>
    @GET("")
    suspend fun getListMode(): Response<String>
    @GET("3/movie/{id}")
    suspend fun getDetails(@Path("id") id: Long): Response<String>
}