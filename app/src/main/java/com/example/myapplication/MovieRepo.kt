package com.example.myapplication

import MovieResponse
import androidx.paging.PagingConfig

class MovieRepo {
    suspend fun getMovie(apiKey:String): MovieResponse = RetrofitBuilder.api.getMovie(apiKey)

    suspend fun getNewMovie(apiKey: String): MovieResponse = RetrofitBuilder.api.getNewMovie(apiKey)

    suspend fun getPopularMovie(apiKey: String): MovieResponse = RetrofitBuilder.api.getPopularMovie(apiKey)

}