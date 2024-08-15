package com.example.myapplication.ViewModel

import com.example.myapplication.Repository.MovieRepoWithPaging
import com.example.myapplication.Data.MovieResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.myapplication.Data.FilterObj
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn


class NewMovieViewModel(): ViewModel()
{
    private val newMovieRepo = MovieRepoWithPaging()

    private val _filterObj = MutableStateFlow<FilterObj?>(null)
    private val filterObj: StateFlow<FilterObj?> = _filterObj

    val popularMoviesFlow: Flow<PagingData<MovieResult>> = newMovieRepo.getPopularMovies()
        .cachedIn(viewModelScope)

    val topRatedMoviesFlow: Flow<PagingData<MovieResult>> = newMovieRepo.getTopRatedMovies()
        .cachedIn((viewModelScope))

    val upComingMovies: Flow<PagingData<MovieResult>> = newMovieRepo.getUpcomingMovies()
        .cachedIn((viewModelScope))


    private val _searchQuery = MutableStateFlow<String?>(null) // MutableStateFlow for search query
    val searchQuery: StateFlow<String?> get() = _searchQuery

    //@OptIn(ExperimentalCoroutinesApi::class)
    private val _moviesFlow = _searchQuery
        .flatMapLatest { query ->
            when {
                query.isNullOrEmpty() -> newMovieRepo.getPopularMovies()
                else -> newMovieRepo.searchMovies(query)
            }
        }
        .cachedIn(viewModelScope) // Cache results in the viewModelScope
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty()) // Convert to StateFlow
    val moviesFlow: StateFlow<PagingData<MovieResult>> get() = _moviesFlow

    // Flow to fetch movies based on filter object

    val filterMoviesFlow = filterObj.flatMapLatest { filter ->
        newMovieRepo.filterMovies(filter)
    }.cachedIn(viewModelScope)


    fun updateSearchQuery(query: String)
    {
        _searchQuery.value = query
    }

    //filter
    fun setFilterData(newFilterObj: FilterObj?)
    {
        _filterObj.value = newFilterObj
    }




}
