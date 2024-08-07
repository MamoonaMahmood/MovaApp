package com.example.myapplication.PagingSource

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.myapplication.Data.MovieResult
import com.example.myapplication.Network.ApiRequestHandle
import java.io.IOException


class SearchPagingSource(
    private val apiService: ApiRequestHandle,
    private val queryStr: String
) : PagingSource<Int, MovieResult>()
{


    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResult> {
         return try{
            val pageNumber = params.key ?: 1
            val response = apiService.SearchMovies(apiKey, query = queryStr,page = pageNumber )
            if (response.results.isNotEmpty()){
                LoadResult.Page(
                    data = response.results,
                    prevKey = if (pageNumber == 1 ) null else pageNumber - 1, // Only paging forward.
                    nextKey = if(response.results.isEmpty()) null else pageNumber + 1 //???????
                )
            }
            else{
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }

        } catch (e: IOException)
        {
            LoadResult.Error(e)

        }
        catch (e: HttpException)
        {
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