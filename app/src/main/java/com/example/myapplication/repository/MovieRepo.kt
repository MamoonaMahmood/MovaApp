package com.example.myapplication.repository

import MovieResponse
import com.example.myapplication.ImageLoad
import com.example.myapplication.RetrofitBuilder

class MovieRepo {
    suspend fun getMovie(apiKey:String): MovieResponse = RetrofitBuilder.api.getMovie(apiKey)
}