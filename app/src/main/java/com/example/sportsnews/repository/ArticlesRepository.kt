package com.example.sportsnews.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.example.sportsnews.database.ArticlesDatabase
import com.example.sportsnews.database.asDomainModel
import com.example.sportsnews.domain.NewsArticle
import com.example.sportsnews.domain.emptyArticle
import com.example.sportsnews.model.ArticlePagingSource
import com.example.sportsnews.model.ArticleRemoteMediator
import com.example.sportsnews.network.SportsNewsApi
import com.example.sportsnews.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext

@OptIn(androidx.paging.ExperimentalPagingApi::class)
class ArticlesRepository(
    private val database: ArticlesDatabase
) {
    private val networkService = SportsNewsApi.retrofitService

    val articlesFlow = database.articlesDao.getNews().map {
        it.asDomainModel()
    }

    suspend fun getArticleFromDatabase(url: String): NewsArticle {
        return database.articlesDao.getArticle(url = url).asDomainModel()
    }

    suspend fun refreshArticles() {
        try {
            withContext(Dispatchers.IO) {
                val data = networkService.getLatestSportsNews()
                database.articlesDao.insertAll(data.asDatabaseModel())
            }
        } catch (e: Exception) {
            Log.e("Nikola", "refreshArticles: ${e.cause} || ${e.message}", )
        }
    }

    val pager = Pager(
        config = PagingConfig(pageSize = 20),
        remoteMediator = ArticleRemoteMediator(database = database, networkService = networkService)
    ) {
        database.articlesDao.pagingSource()
    }.flow.map { page ->
        page.map { it.asDomainModel() }
    }
}