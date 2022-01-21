package com.whocanfly.movieapp.views.movie.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whocanfly.movieapp.api.MovieContainer
import com.whocanfly.movieapp.api.MovieRepo
import com.whocanfly.movieapp.api.Video
import com.whocanfly.movieapp.api.VideoContainer
import kotlinx.coroutines.launch
import timber.log.Timber

class MovieDetailViewModel(private val movieRepo: MovieRepo) : ViewModel() {

    val videos: MutableLiveData<Video> = MutableLiveData()

    fun fetchMovieVideos(id: Int) {
        viewModelScope.launch {
            val movieVideoResponse = movieRepo.getMovieVideos(id)
            if (movieVideoResponse.isSuccessful) {
                videos.postValue(movieVideoResponse.body()!!.results[0])
            }
        }
    }
}