package com.example.sportsnews.network

import com.example.sportsnews.API_KEY
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://newsapi.org/"
private const val VERSION = "v2"

interface SportsNewsApiService {
    @GET("$VERSION/top-headlines?country=us&apiKey=$API_KEY")
    suspend fun getLatestSportsNews(
        @Query("page") pageNumber: Int,
        @Query("pageSize") pageSize: Int,
        //@Query("country") country: String = "us",
        @Query("category") category: String = "sports"
    ): NewsResponse

    @GET("$VERSION/top-headlines?country=us&apiKey=$API_KEY")
    suspend fun getLatestSportsNews(
        @Query("category") category: String = "sports"
    ): NewsResponse
}

object SportsNewsApi {
    val retrofitService: SportsNewsApiService by lazy {
        retrofit.create(SportsNewsApiService::class.java)
    }
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()