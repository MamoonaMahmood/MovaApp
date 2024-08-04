package com.example.myapplication.ViewModel

import com.example.myapplication.Repository.MovieRepoWithPaging
import com.example.myapplication.Data.MovieResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow


class NewMovieViewModel(private val movieRepo: MovieRepoWithPaging): ViewModel()
{

    val popularMoviesFlow: Flow<PagingData<MovieResult>> = movieRepo.getPopularMovies()
        .cachedIn(viewModelScope)

    val topRatedMoviesFlow: Flow<PagingData<MovieResult>> = movieRepo.getTopRatedMovies()
        .cachedIn((viewModelScope))

    val upComingMovies: Flow<PagingData<MovieResult>> = movieRepo.getUpcomingMovies()
        .cachedIn((viewModelScope))
}

class NewMovieViewModelFactory(private val movieRepo: MovieRepoWithPaging) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewMovieViewModel::class.java)) {
            return NewMovieViewModel(movieRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}