package com.whocanfly.movieapp.api

import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.whocanfly.movieapp.utils.Definitions
import kotlinx.parcelize.Parcelize
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

@Parcelize
data class Movie(
    @SerializedName("original_language") val originalLanguage: String? = null,
    @SerializedName("imdb_id") val imdbId: String? = null,
    @SerializedName("videos") val videos: Video? = null,
    @SerializedName("vide   o") val video: Boolean? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("backdrop_path") val backdropPath: String? = null,
    @SerializedName("revenue") val revenue: Long? = null,
    @SerializedName("genres") val genres: List<GenresItem>? = null,
    @SerializedName("popularity") val popularity: Double? = null,
    @SerializedName("id") val id: Int? = null,
    @SerializedName("vote_count") val voteCount: Int? = null,
    @SerializedName("budget") val budget: Long? = null,
    @SerializedName("overview") val overview: String? = null,
    @SerializedName("original_title") val originalTitle: String? = null,
    @SerializedName("runtime") val runtime: Int? = null,
    @SerializedName("poster_path") val posterPath: String? = null,
    @SerializedName("release_date") val releaseDate: String? = null,
    @SerializedName("vote_average") val voteAverage: Double? = null,
    @SerializedName("tagline") val tagline: String? = null,
    @SerializedName("adult") val adult: Boolean? = null,
    @SerializedName("homepage") val homepage: String? = null,
    @SerializedName("status") val status: String? = null
) : Parcelable {

    fun getBackDropImageUrl(): String {
        return Definitions.IMAGE_ENDPOINT + backdropPath
    }

    fun getCompleteImageUrl(): String {
        return Definitions.IMAGE_URL_W500 + posterPath
    }

    override fun toString(): String {
        return "[original_language = $originalLanguage, imdb_id = $imdbId, video = $video, title = $title, backdrop_path = $backdropPath, revenue = $revenue, genres = $genres, popularity = $popularity, id = $id, vote_count = $voteCount, budget = $budget, overview = $overview, original_title = $originalTitle, runtime = $runtime, poster_path = $posterPath, release_date = $releaseDate, vote_average = $voteAverage, tagline = $tagline, adult = $adult, homepage = $homepage, status = $status]"
    }
}

@Parcelize
data class VideoContainer(
    @SerializedName("id")val id: Int,
    @SerializedName("results")val results: List<Video>
) : Parcelable

@Parcelize
data class Video(
    @SerializedName("id") val id: String,
    @SerializedName("iso_3166_1") val iso_3166_1: String,
    @SerializedName("iso_639_1") val iso_639_1: String,
    @SerializedName("key") val key: String,
    @SerializedName("name") val name: String,
    @SerializedName("site") val site: String,
    @SerializedName("size") val size: Int,
    @SerializedName("type") val type: String
) : Parcelable

@Parcelize
data class GenresItem(
    @SerializedName("name") val name: String? = null,
    @SerializedName("id") val id: Int? = null
) : Parcelable

@Parcelize
data class Search(
    @SerializedName("page") val page: Int? = null,
    @SerializedName("total_pages") val totalPages: Int? = null,
    @SerializedName("results") val searchResultsList: List<SearchItem>? = null,
    @SerializedName("total_results") val totalResults: Int? = null
) : Parcelable

@Parcelize
data class SearchItem(
    @SerializedName("first_air_date") val firstAirDate: String? = null,
    @SerializedName("overview") val overview: String? = null,
    @SerializedName("original_language") val originalLanguage: String? = null,
    @SerializedName("genre_ids") val genreIds: List<Int>? = null,
    @SerializedName("poster_path") val posterPath: String? = null,
    @SerializedName("origin_country") val originCountry: List<String>? = null,
    @SerializedName("backdrop_path") val backdropPath: String? = null,
    @SerializedName("media_type") val mediaType: String? = null,
    @SerializedName("original_name") val originalName: String? = null,
    @SerializedName("vote_average") val voteAverage: Double? = null,
    @SerializedName("popularity") val popularity: Double? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("id") val id: Int? = null,
    @SerializedName("vote_count") val voteCount: Int? = null,
    @SerializedName("original_title") val originalTitle: String? = null,
    @SerializedName("video") val video: Boolean? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("release_date") val releaseDate: String? = null,
    @SerializedName("adult") val adult: Boolean? = null
) : Parcelable

@Parcelize
data class MovieContainer(
    @SerializedName("page") val page: Int? = null,
    @SerializedName("total_pages") val totalPages: Int? = null,
    @SerializedName("results") val searchResultsList: List<Movie>? = null,
    @SerializedName("total_results") val totalResults: Int? = null
) : Parcelable
