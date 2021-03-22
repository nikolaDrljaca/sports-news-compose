package com.example.sportsnews.model

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.sportsnews.database.ArticlesDatabase
import com.example.sportsnews.database.DatabaseNewsArticle
import com.example.sportsnews.network.NetworkArticle
import com.example.sportsnews.network.SportsNewsApiService
import com.example.sportsnews.network.asDatabaseModel
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class ArticleRemoteMediator(
    private val category: String = "sports",
    private val database: ArticlesDatabase,
    private val networkService: SportsNewsApiService
): RemoteMediator<Int, DatabaseNewsArticle>() {
    private val dao = database.articlesDao


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, DatabaseNewsArticle>
    ): MediatorResult {
        return try {

            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()

                    if (lastItem == null) {
                        return MediatorResult.Success(endOfPaginationReached = false)
                    }
                    lastItem.tableId
                }
            }

            val response = networkService.getLatestSportsNews()
            database.withTransaction {
                if (loadType == LoadType.REFRESH)
                    dao.clearArticles()

                dao.insertAll(response.asDatabaseModel())
            }

            val nextPage = if (state.pages.isEmpty()) 1 else state.pages.last().nextKey
            MediatorResult.Success(endOfPaginationReached = nextPage == null)

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}