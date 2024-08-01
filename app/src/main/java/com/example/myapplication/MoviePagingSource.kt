package com.example.myapplication

import MovieResult
import android.graphics.pdf.PdfDocument.Page
import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.paging.PagingSource
import androidx.paging.PagingState
import okio.IOException
import java.lang.Error
import kotlin.coroutines.CoroutineContext

class MoviePagingSource(
    private val apiService: ApiRequestHandle,
    private val apiKey: String
) : PagingSource<Int,MovieResult>()
{
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResult> {
        try {
            // Start refresh at page 1 if undefined.
            val pageNumber = params.key ?: 1
            val response = apiService.getPopularMovie(apiKey, page = pageNumber)
            return LoadResult.Page(
                data = response.results,
                prevKey = if (pageNumber == 1 ) null else 1, // Only paging forward.
                nextKey = if( response.results.isEmpty()) null else pageNumber + 1
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
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