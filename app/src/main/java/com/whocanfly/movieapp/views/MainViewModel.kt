package com.whocanfly.movieapp.views

import androidx.lifecycle.*
import com.whocanfly.movieapp.api.Movie
import com.whocanfly.movieapp.api.MovieRepo
import com.whocanfly.movieapp.api.UpcomingMovie
import com.whocanfly.movieapp.api.UpcomingMovieResult
import com.whocanfly.movieapp.utils.Definitions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val movieRepo: MovieRepo) : ViewModel() {

    fun fetchAllMoviesFromApi() {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepo.fetchAllMoviesFromApi()
        }
    }
    val upcomingMoviesData = getUpcomingMovies().value

    fun getUpcomingMovies(): MutableLiveData<List<Movie>?> {
        return movieRepo.getUpcomingMovies()
    }

    fun getPopularMovies(): MutableLiveData<List<Movie>?> {
        return movieRepo.getPopularMovies()
    }

    fun getTrendingMovies(): MutableLiveData<List<Movie>?> {
        return movieRepo.getTrendingMovies()
    }

    fun getTopRatedMovies(): MutableLiveData<List<Movie>?> {
        return movieRepo.getTopRatedMovies()
    }

    fun getPopularSeries(): MutableLiveData<List<Movie>?> {
        return movieRepo.getPopularSeries()
    }
}