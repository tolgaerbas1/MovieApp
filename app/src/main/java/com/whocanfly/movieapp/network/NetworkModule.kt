package com.whocanfly.movieapp.network

import com.google.gson.GsonBuilder
import com.whocanfly.movieapp.BuildConfig
import com.whocanfly.movieapp.BuildConfig.DEBUG
import com.whocanfly.movieapp.api.MovieAPI
import com.whocanfly.movieapp.api.MovieRepo
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { provideOkHttpClient() }
    single { provideMovieApi(get()) }
    single { provideRetrofit(get()) }
    single { MovieRepo(get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
        .build()
}

val connectTimeout : Long = 40// 20s
val readTimeout : Long  = 40 // 20s

fun provideOkHttpClient(): OkHttpClient {
    val okHttpClientBuilder = OkHttpClient.Builder()
        .connectTimeout(connectTimeout, TimeUnit.SECONDS)
        .readTimeout(readTimeout, TimeUnit.SECONDS)
    if (DEBUG) {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)
    }
    okHttpClientBuilder.build()
    return okHttpClientBuilder.build()
}

fun provideMovieApi(retrofit: Retrofit): MovieAPI =
    retrofit.create(MovieAPI::class.java)
