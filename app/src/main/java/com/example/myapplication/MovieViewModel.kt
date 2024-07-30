package com.example.myapplication

import MovieResponse
import MovieResult
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


public class MovieViewModel(private val movieRepo: MovieRepo): ViewModel() {

    private val _movieStateFlow = MutableStateFlow<MovieResponse?>(null)
    val movieStateFlow = _movieStateFlow
    private val _newMovieStateFlow = MutableStateFlow<MovieResponse?>(null)
    val newMovieStateFlow = _newMovieStateFlow
    private val _popMovieStateFlow = MutableStateFlow<MovieResponse?>(null)
    val popMovieStateFlow = _popMovieStateFlow

    private val apiKey="316373081224cd654e971158dc41dc51"

    public constructor() : this(MovieRepo()) {
        // Optional initialization if needed
    }

    fun fetchMovie()
    {
        viewModelScope.launch {
            try {
                val response = movieRepo.getMovie(apiKey)
                _movieStateFlow.value = response
            }
            catch(e: Exception)
            {
                Log.e("MovieViewModel", "Error fetching movie", e)
                _movieStateFlow.value = null
            }

        }
    }

    fun fetchUpcomingMovies()
    {
        viewModelScope.launch {
            try {
                val response = movieRepo.getNewMovie(apiKey)
                _newMovieStateFlow.value = response
            }
            catch (e: Exception)
            {
                Log.e("MovieViewModel", "Error fetching upcoming movie", e)
                _newMovieStateFlow.value = null
            }
        }
    }
    fun fetchPopularMovies()
    {
        viewModelScope.launch {
            try {
                val response = movieRepo.getPopularMovie(apiKey)
                _popMovieStateFlow.value = response
            }
            catch (e: Exception)
            {
                Log.e("MovieViewModel", "Error fetching popular movie", e)
                _popMovieStateFlow.value = null
            }
        }
    }


}