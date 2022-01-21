package com.whocanfly.movieapp.network

import android.content.Context
import com.whocanfly.movieapp.views.movie.MainViewModel
import com.whocanfly.movieapp.views.movie.detail.MovieDetailViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val koinModule = module {
    single {
        androidApplication().getSharedPreferences(
            "Movie App",
            Context.MODE_PRIVATE
        )
    }
    viewModel { MainViewModel(get()) }
    viewModel { MovieDetailViewModel(get()) }
}