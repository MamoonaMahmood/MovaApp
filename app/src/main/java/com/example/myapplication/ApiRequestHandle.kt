package com.example.myapplication

import MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiRequestHandle {

    @GET("movie/top_rated")
    suspend fun getMovie(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ):MovieResponse

    @GET("movie/upcoming")
    suspend fun getNewMovie(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ):MovieResponse

    @GET("movie/popular")
    suspend fun getPopularMovie(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ):MovieResponse

}