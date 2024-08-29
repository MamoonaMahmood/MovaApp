package com.example.myapplication.ViewModel


import com.example.myapplication.Repository.MovieRepoWithPaging
import com.example.myapplication.Data.MovieResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.room.util.query
import com.example.myapplication.Data.FilterObj
import com.example.myapplication.Data.MovieResponse
import com.example.myapplication.Data.UserDao
import com.example.myapplication.Data.UserData
import com.example.myapplication.MyMovaApp
import com.example.myapplication.Repository.DataBaseRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class NewMovieViewModel(): ViewModel()
{
    //declarations
    private val newMovieRepo = MovieRepoWithPaging()
    private val userDao: UserDao = MyMovaApp.getUserDao()
    private val repository = DataBaseRepo(userDao)


    val readAllDataFlow: Flow<PagingData<UserData>> = repository.readPagedData().cachedIn(viewModelScope)

    fun addUserLikes(userData: UserData)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUserLike(userData)
        }
    }

    fun deleteUserLike(userData: UserData)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(userData)
        }
    }

    fun deleteAllUsers()
    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllUsers()
        }
    }


    private val _searchQuery = MutableStateFlow<String?>(null) // Search query
    val searchQuery: StateFlow<String?> = _searchQuery.asStateFlow()

    private val _filterObj = MutableStateFlow<FilterObj?>(null)
    private val filterObj: StateFlow<FilterObj?> get() = _filterObj

    private val _viewMode = MutableStateFlow<ViewMode>(ViewMode.Popular)
    val viewMode: StateFlow<ViewMode> get() = _viewMode

    private val _bannerMoviesFlow = MutableStateFlow<MovieResponse?>(null)
    val bannerMoviesFlow: StateFlow<MovieResponse?> get() = _bannerMoviesFlow


    //simple flows
    val topRatedMoviesFlow: Flow<PagingData<MovieResult>> = newMovieRepo.getTopRatedMovies()
        .cachedIn((viewModelScope))

    val upComingMovies: Flow<PagingData<MovieResult>> = newMovieRepo.getUpcomingMovies()
        .cachedIn((viewModelScope))

    fun fetchBannerMovies() {
        viewModelScope.launch {
            try {
                val response = newMovieRepo.staticPopMovies()
                _bannerMoviesFlow.value = response

            } catch (e:Exception)
            {
                _bannerMoviesFlow.value = null
            }
        }
    }

    private val combinedFlow = combine(
        searchQuery,filterObj,viewMode
    ){
        query, filter, mode ->
        Triple(query, filter ?: FilterObj.default(), mode)
    }.flatMapLatest {
        (query, filter, mode) ->
            when(mode) {
                ViewMode.Popular -> newMovieRepo.getPopularMovies()
                ViewMode.Search -> {
                    if(query.isNullOrEmpty()){
                        newMovieRepo.getPopularMovies()
                    }else{
                        newMovieRepo.searchMovies(query)
                    }
                }
                ViewMode.Filter -> {
                    if (filter == FilterObj.default()) {
                        newMovieRepo.getPopularMovies() // Fallback to popular movies if filter is default
                    } else {
                        newMovieRepo.filterMovies(filter)
                    }
                }
            }
    }.cachedIn(viewModelScope) // Cache results in the viewModelScope
    .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    val moviesFlow: StateFlow<PagingData<MovieResult>> get() = combinedFlow



    fun updateSearchQuery(query: String)
    {
        _searchQuery.value = query
        _viewMode.value = ViewMode.Search
    }

    //filter
    fun setFilterData(newFilterObj: FilterObj?)
    {
        _filterObj.value = newFilterObj ?: FilterObj.default()
        _viewMode.value = ViewMode.Filter
    }
    fun showPopularMovies() {
        _viewMode.value = ViewMode.Popular
    }


    enum class ViewMode {
        Popular,
        Search,
        Filter
    }



}
