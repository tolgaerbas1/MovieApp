package com.whocanfly.movieapp.api

import androidx.lifecycle.MutableLiveData
import com.whocanfly.movieapp.utils.Definitions
import retrofit2.Response

class MovieRepo(private val movieAPI: MovieAPI) {

    suspend fun getMovieDetails(movieId: Int) =
        movieAPI.getMovieDetails(movieId, Definitions.API_KEY)

    suspend fun getPopularMovies(page: Int) = movieAPI.getPopularMovies(Definitions.API_KEY, page)

    suspend fun getTopRated() = movieAPI.getTopRatedMovies(Definitions.API_KEY)

    suspend fun getUpcoming() = movieAPI.getUpcomingMovie(Definitions.API_KEY)

    suspend fun getTrending(media_type: String, time_window: String) =
        movieAPI.getTrendingMovies(media_type, time_window, Definitions.API_KEY)

    suspend fun getMovieVideos(id:Int):Response<VideoContainer>{
        return movieAPI.getMovieVideos(id,Definitions.API_KEY)
    }

    suspend fun getSimilar(type:String,id:Int):Response<MovieContainer>{
        return movieAPI.getSimilar(id,Definitions.API_KEY)
    }
    private var upcomingMovies: MutableLiveData<List<Movie>?> = MutableLiveData()
    private var popularMovies: MutableLiveData<List<Movie>?> = MutableLiveData()
    private var trendingMovies: MutableLiveData<List<Movie>?> = MutableLiveData()
    private var topRatedMovies: MutableLiveData<List<Movie>?> = MutableLiveData()
    private var popularSeries: MutableLiveData<List<Movie>?> = MutableLiveData()


    suspend fun fetchAllMoviesFromApi() {

        val upcomingMovieResponse = getUpcoming()
        if (upcomingMovieResponse.isSuccessful) {
            val list = upcomingMovieResponse.body()?.searchResultsList
            upcomingMovies.postValue(list)
        }

        val popularMovieResponse = getPopularMovies(1)
        if (popularMovieResponse.isSuccessful) {
            val list = popularMovieResponse.body()?.searchResultsList
            popularMovies.postValue(list)
        }

        val trendingMovieResponse =
            getTrending(Definitions.IS_MOVIE, Definitions.TIME_WINDOW)
        if (trendingMovieResponse.isSuccessful) {
            val list = trendingMovieResponse.body()?.searchResultsList
            trendingMovies.postValue(list)
        } else {
            println("Error in response of Trending Movies : " + trendingMovieResponse.errorBody())
        }

        val topRatedMovieResponse = getTopRated()
        if (topRatedMovieResponse.isSuccessful) {
            val list = topRatedMovieResponse.body()?.searchResultsList
            topRatedMovies.postValue(list)
        }

        val popularSeriesResponse = getPopularMovies(1)
        if (popularSeriesResponse.isSuccessful) {
            val list = popularSeriesResponse.body()?.searchResultsList
            popularSeries.postValue(list)
        }

    }

    fun getUpcomingMovies(): MutableLiveData<List<Movie>?> {
        return upcomingMovies
    }

    fun getPopularMovies(): MutableLiveData<List<Movie>?> {
        return popularMovies
    }

    fun getTrendingMovies(): MutableLiveData<List<Movie>?> {
        return trendingMovies
    }

    fun getTopRatedMovies(): MutableLiveData<List<Movie>?> {
        return topRatedMovies
    }

    fun getPopularSeries(): MutableLiveData<List<Movie>?> {
        return popularSeries
    }

}