package com.whocanfly.movieapp

import android.app.Application
import android.content.Context
import com.whocanfly.movieapp.network.koinModule
import com.whocanfly.movieapp.network.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieApp : Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this@MovieApp

        startKoin {
            androidContext(this@MovieApp)
            modules(koinModule, networkModule)
        }
    }
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }
    companion object {
        private var INSTANCE: MovieApp? = null
        fun getInstance() = INSTANCE
    }
}