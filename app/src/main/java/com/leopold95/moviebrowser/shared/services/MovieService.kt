package com.leopold95.moviebrowser.shared.services

import com.leopold95.moviebrowser.BuildConfig
import com.leopold95.moviebrowser.shared.JsonParser
import com.leopold95.moviebrowser.shared.RequestPromise
import com.leopold95.moviebrowser.shared.abstraction.IMovieService
import com.leopold95.moviebrowser.shared.models.DetailsMovieModel
import com.leopold95.moviebrowser.shared.models.ShortMovieModel
import com.leopold95.moviebrowser.shared.services.api.ApiMovieDetailsModel
import com.leopold95.moviebrowser.shared.services.api.ApiMovieListModel
import com.leopold95.moviebrowser.shared.services.api.IMovieApi
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieService @Inject constructor(
    private val parser: JsonParser,
) : IMovieService {
    private val clientHttp = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original: Request = chain.request()
            val request: Request = original.newBuilder()
                .header("Authorization", "Bearer " + BuildConfig.TMDB_API_KEY)
                .header("Accept", "application/json")
                .build()
            chain.proceed(request)
        }
        .build()

    private val movieApi = Retrofit.Builder()
        .baseUrl(BuildConfig.TMB_API_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .client(clientHttp)
        .build()
        .create(IMovieApi::class.java)

    private val imageUrlPrefix = BuildConfig.TMB_API_IMAGES

    override suspend fun getList(): RequestPromise<List<ShortMovieModel>> {
        try {
            val responce = movieApi.getList(1)
            if(!responce.isSuccessful)
                return RequestPromise.error(responce.message())

            if (responce.body().isNullOrEmpty()){
                return RequestPromise.error("response string is null or empty")
            }

            val model = parser.format<ApiMovieListModel>(responce.body()!!)

            val list = mutableListOf<ShortMovieModel>()

            for(item in model.results){
                list.add(ShortMovieModel(
                    id = item.id,
                    name = item.title,
                    imageUrl = imageUrlPrefix + item.posterPath,
                    description = item.overview
                ))
            }

            return RequestPromise.ok(list)
        }
        catch (ex: Exception){
            return RequestPromise.error(ex.message)
        }
    }

    override suspend fun getDetails(id: Long): RequestPromise<DetailsMovieModel> {
        try {
            val responce = movieApi.getDetails(id)
            if(!responce.isSuccessful)
                return RequestPromise.error(responce.message())

            if (responce.body().isNullOrEmpty()){
                return RequestPromise.error("response string is null or empty")
            }

            val responseModel = parser.format<ApiMovieDetailsModel>(responce.body()!!)

            val model = DetailsMovieModel(
                id = responseModel.id,
                title = responseModel.title,
                description = responseModel.overview,
                imagePath = imageUrlPrefix + responseModel.posterPath,
                geners = responseModel.genres.map { it.name },
                rating = responseModel.voteAverage.toFloat(),
                releaseYear = com.leopold95.moviebrowser.shared.ReleaseDateFormatter.extractYear(responseModel.releaseDate),
                duration = responseModel.runtime
            )

            return RequestPromise.ok(model)
        }
        catch (ex: Exception){
            return RequestPromise.error(ex.message)
        }
    }
}