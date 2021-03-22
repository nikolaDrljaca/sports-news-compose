package com.example.sportsnews.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.sportsnews.domain.NewsArticle

@Entity(tableName = "news")
data class DatabaseNewsArticle(
    @PrimaryKey(autoGenerate = true) val tableId: Int = 0,
    @Embedded val source: DatabaseNewsSource,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?,
) {
    data class DatabaseNewsSource(
        val id: String?,
        val name: String
    )
}

//maps database objects to domain objects (ones displayed and manipulated in the UI)
fun List<DatabaseNewsArticle>.asDomainModel(): List<NewsArticle> {
    return map {
        NewsArticle(
            source = NewsArticle.NewsSource(
                id = it.source.id ?: "No ID found.",
                name = it.source.name
            ),
            author = it.author ?: "Unknown author.",
            title = it.title,
            description = it.description ?: "No description available.",
            url = it.url,
            urlToImage = it.urlToImage ?: " ",
            publishedAt = it.publishedAt,
            content = it.content ?: "No content available. "
        )
    }
}

fun DatabaseNewsArticle.asDomainModel(): NewsArticle {
    return NewsArticle(
        source = NewsArticle.NewsSource(
            id = this.source.id ?: "No ID found.",
            name = this.source.name
        ),
        author = this.author ?: "Unknown author.",
        title = this.title,
        description = this.description ?: "No description available.",
        url = this.url,
        urlToImage = this.urlToImage ?: " ",
        publishedAt = this.publishedAt,
        content = this.content ?: "No content available. "
    )
}