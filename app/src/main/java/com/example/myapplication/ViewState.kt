package com.example.myapplication

import androidx.paging.PagingData
import com.example.myapplication.Data.MovieResult


data class MovieViewState(
    val movies: PagingData<MovieResult> = PagingData.empty(),
    val showErrorImage: Boolean = false // New flag to indicate whether to show the error image
)