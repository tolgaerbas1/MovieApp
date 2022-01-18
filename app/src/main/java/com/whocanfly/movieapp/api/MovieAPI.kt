package com.whocanfly.movieapp.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPI {


    @GET("movie/{movieId}")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<Movie>

    @GET("trending/{media_type}/{time_window}")
    suspend fun getTrendingMovies(
        @Path("media_type") media_type: String,
        @Path("time_window") time_window: String,
        @Query("api_key") api_key: String
    ): Response<MovieContainer>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") api_key: String
    ): Response<MovieContainer>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovie(
        @Query("api_key") api_key: String
    ): Response<MovieContainer>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<MovieContainer>

    @GET("movie/{id}/videos")
    suspend fun getMovieVideos(
        @Path("id") id:Int,
        @Query("api_key") api_key:String
    ):Response<VideoContainer>

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilar(
        @Path("movie_id") movie_id:Int,
        @Query("api_key") api_key:String
    ):Response<MovieContainer>
}