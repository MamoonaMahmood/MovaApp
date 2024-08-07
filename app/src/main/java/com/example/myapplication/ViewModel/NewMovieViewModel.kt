package com.example.myapplication.ViewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.myapplication.Repository.MovieRepoWithPaging
import com.example.myapplication.Data.MovieResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.myapplication.MovieViewState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class NewMovieViewModel(): ViewModel()
{
    private val newMovieRepo = MovieRepoWithPaging()



    val popularMoviesFlow: Flow<PagingData<MovieResult>> = newMovieRepo.getPopularMovies()
        .cachedIn(viewModelScope)

    val topRatedMoviesFlow: Flow<PagingData<MovieResult>> = newMovieRepo.getTopRatedMovies()
        .cachedIn((viewModelScope))

    val upComingMovies: Flow<PagingData<MovieResult>> = newMovieRepo.getUpcomingMovies()
        .cachedIn((viewModelScope))


    private val _searchQuery = MutableStateFlow<String?>(null) // MutableStateFlow for search query
    val searchQuery: StateFlow<String?> get() = _searchQuery


    private val _moviesFlow = _searchQuery
        .flatMapLatest { query ->
            if (query.isNullOrEmpty()) {
                newMovieRepo.getPopularMovies() // Provide popular movies when no query
            } else {
                newMovieRepo.searchMovies(query) // Provide search results when query is present
            }
        }
        .cachedIn(viewModelScope) // Cache results in the viewModelScope
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty()) // Convert to StateFlow

    val moviesFlow: StateFlow<PagingData<MovieResult>> get() = _moviesFlow


    fun updateSearchQuery(query: String)
    {
        _searchQuery.value = query
    }
}
