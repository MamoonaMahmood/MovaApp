import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.myapplication.ApiRequestHandle
import com.example.myapplication.MoviePagingSource
import kotlinx.coroutines.flow.Flow

class MovieRepoWithPaging(private val apiService: ApiRequestHandle) {

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
}
