package com.example.sportsnews.network

import android.os.Parcelable
import com.example.sportsnews.database.DatabaseNewsArticle
import kotlinx.parcelize.Parcelize

data class NewsResponse(
    val articles: List<NetworkArticle>
)

@Parcelize
data class NetworkArticle(
    val source: NetworkSource,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?,
): Parcelable {
    @Parcelize
    data class NetworkSource(
        val id: String?,
        val name: String
    ): Parcelable
}

//map the network response to database model, savable
fun NewsResponse.asDatabaseModel(): List<DatabaseNewsArticle> {
    return articles.map {
        DatabaseNewsArticle(
            source = DatabaseNewsArticle.DatabaseNewsSource(
                id = it.source.id,
                name = it.source.name
            ),
            author = it.author,
            title = it.title,
            description = it.description,
            urlToImage = it.urlToImage,
            url = it.url,
            publishedAt = it.publishedAt,
            content = it.content
        )
    }
}