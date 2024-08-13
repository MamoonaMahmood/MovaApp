package com.example.myapplication.Network

import com.example.myapplication.Data.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.Year

interface ApiRequestHandle {

    @GET("movie/top_rated")
    suspend fun getMovie(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): MovieResponse

    @GET("movie/upcoming")
    suspend fun getNewMovie(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): MovieResponse

    @GET("movie/popular")
    suspend fun getPopularMovie(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): MovieResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1

    ):MovieResponse

    @GET("discover/movie")
    suspend fun filterMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("region") region: String?,
        @Query("sort_by") sort: String?,
        @Query("with_genres") genre: String?,
        @Query("year") year: String?,
        @Query("page") page: Int = 1

    ): MovieResponse


}