package com.example.myapplication.Repository

import com.example.myapplication.Data.MovieResult
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.myapplication.Network.ApiRequestHandle
import com.example.myapplication.Network.RetrofitBuilder
import com.example.myapplication.PagingSource.MoviePagingSource
import com.example.myapplication.PagingSource.SearchPagingSource
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit

class MovieRepoWithPaging() {

    private val apiService = RetrofitBuilder.create()
    fun getTopRatedMovies(): Flow<PagingData<MovieResult>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingSource(apiService, "movie/top_rated") }
        ).flow
    }

    fun getUpcomingMovies(): Flow<PagingData<MovieResult>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingSource(apiService, "movie/upcoming") }
        ).flow
    }

    fun getPopularMovies(): Flow<PagingData<MovieResult>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingSource(apiService, "movie/popular") }
        ).flow
    }

    fun searchMovies(queryStr: String) : Flow<PagingData<MovieResult>>
    {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { SearchPagingSource(apiService, queryStr)}
        ).flow
    }
}
