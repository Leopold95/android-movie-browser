package com.leopold95.moviebrowser.shared.services.api

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ApiMovieDetailsModel(
    @SerializedName("adult")
    val adult: Boolean,

    @SerializedName("backdrop_path")
    val backdropPath: String?,

    @SerializedName("belongs_to_collection")
    val belongsToCollection: CollectionDto?,

    @SerializedName("budget")
    val budget: Long,

    @SerializedName("genres")
    val genres: List<GenreDto>,

    @SerializedName("homepage")
    val homepage: String?,

    @SerializedName("id")
    val id: Long,

    @SerializedName("imdb_id")
    val imdbId: String?,

    @SerializedName("origin_country")
    val originCountry: List<String>,

    @SerializedName("original_language")
    val originalLanguage: String,

    @SerializedName("original_title")
    val originalTitle: String,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("popularity")
    val popularity: Double,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompanyDto>,

    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountryDto>,

    @SerializedName("release_date")
    val releaseDate: String,

    @SerializedName("revenue")
    val revenue: Long,

    @SerializedName("runtime")
    val runtime: Int,

    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguageDto>,

    @SerializedName("status")
    val status: String,

    @SerializedName("tagline")
    val tagline: String?,

    @SerializedName("title")
    val title: String,

    @SerializedName("video")
    val video: Boolean,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("vote_count")
    val voteCount: Int
)

@Serializable
data class CollectionDto(
    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("backdrop_path")
    val backdropPath: String?
)

@Serializable
data class GenreDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String
)

@Serializable
data class ProductionCompanyDto(
    @SerializedName("id")
    val id: Long,

    @SerializedName("logo_path")
    val logoPath: String?,

    @SerializedName("name")
    val name: String,

    @SerializedName("origin_country")
    val originCountry: String
)

@Serializable
data class ProductionCountryDto(
    @SerializedName("iso_3166_1")
    val iso3166_1: String,

    @SerializedName("name")
    val name: String
)

@Serializable
data class SpokenLanguageDto(
    @SerializedName("english_name")
    val englishName: String,

    @SerializedName("iso_639_1")
    val iso639_1: String,

    @SerializedName("name")
    val name: String
)