package com.example.sportsnews.model

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.sportsnews.network.NetworkArticle
import com.example.sportsnews.network.SportsNewsApiService

/**
 * @param nextPage used to determine value of next page, initially null so we set it to 1
 * @param response the network response with the list of data, supplied with the next page and page size args
 * @param LoadResult.Page gets supplied with the data
 * @param prevKey the page value for previous page, null if we are on the first page, otherwise just decrease the current page (nextPage)
 * @param nextKey page value for next page, if the list is not empty its nextPage + 1 (the next page)
 * @param getRefreshKey to be ignored
 * */

class ArticlePagingSource(
    private val sportsNewsApiService: SportsNewsApiService,
    private val category: String
): PagingSource<Int, NetworkArticle>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NetworkArticle> {
        return try {
            val nextPage = params.key ?: 1
            val response = sportsNewsApiService.getLatestSportsNews(
                pageNumber = nextPage,
                pageSize = 10,
                category = category
            )


            LoadResult.Page(
                data = response.articles,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (response.articles.isEmpty()) null else nextPage + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, NetworkArticle>): Int? {
        return null
    }
}