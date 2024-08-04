package com.example.myapplication.PagingSource

import com.example.myapplication.Data.MovieResult
import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.myapplication.Network.ApiRequestHandle
import okio.IOException

const val apiKey : String = "316373081224cd654e971158dc41dc51"
class MoviePagingSource(
    private val apiService: ApiRequestHandle,
    private val path: String
) : PagingSource<Int, MovieResult>()
{
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResult> {
        return try {
            // Start refresh at page 1 if undefined.
            val pageNumber = params.key ?: 1
            val response = when (path) {
                "movie/popular" -> apiService.getPopularMovie(apiKey, page = pageNumber)
                "movie/top_rated" -> apiService.getMovie(apiKey, page = pageNumber)
                "movie/upcoming" -> apiService.getNewMovie(apiKey, page = pageNumber)
                else -> throw IllegalArgumentException("Invalid path")
            }
            LoadResult.Page(
                data = response.results,
                prevKey = if (pageNumber == 1 ) null else pageNumber - 1, // Only paging forward.
                nextKey = if(response.results.isEmpty()) null else pageNumber + 1 //???????
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            Log.e("MoviePagingSource", "Error URL")
            LoadResult.Error(e)
        }

    }
    override fun getRefreshKey(state: PagingState<Int, MovieResult>): Int? {
        // Try to find the page key of the closest page to anchorPosition from
        // either the prevKey or the nextKey; you need to handle nullability
        // here.
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey are null -> anchorPage is the
        //    initial page, so return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}