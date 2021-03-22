package com.example.sportsnews.database

import android.content.Context
import androidx.paging.PagingSource
import androidx.room.*
import androidx.room.Dao
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Query("select * from news order by tableId desc")
    fun getNews(): Flow<List<DatabaseNewsArticle>>

    @Query("select * from news")
    fun getNewsList(): List<DatabaseNewsArticle>

    @Query("select * from news where url=:url")
    suspend fun getArticle(url: String): DatabaseNewsArticle

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(articles: List<DatabaseNewsArticle>)

    @Query("delete from news")
    suspend fun clearArticles()

    @Query("select * from news order by tableId desc")
    fun pagingSource(): PagingSource<Int, DatabaseNewsArticle>
}

@Database(entities = [DatabaseNewsArticle::class], version = 2, exportSchema = false)
abstract class ArticlesDatabase: RoomDatabase() {
    abstract val articlesDao: NewsDao
}

private lateinit var INSTANCE: ArticlesDatabase

fun getDatabase(context: Context): ArticlesDatabase {
    synchronized(ArticlesDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                ArticlesDatabase::class.java,
                "news_database"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
    return INSTANCE
}