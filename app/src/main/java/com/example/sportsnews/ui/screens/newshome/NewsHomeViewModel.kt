package com.example.sportsnews.ui.screens.newshome

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.sportsnews.database.ArticlesDatabase
import com.example.sportsnews.database.getDatabase
import com.example.sportsnews.domain.NewsArticle
import com.example.sportsnews.model.ArticlePagingSource
import com.example.sportsnews.network.NetworkArticle
import com.example.sportsnews.network.SportsNewsApi
import com.example.sportsnews.repository.ArticlesRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NewsHomeViewModel(app: Application): AndroidViewModel(app) {
    private val repository = ArticlesRepository(getDatabase(app))

    private val _state = MutableStateFlow(NewsHomeViewState())
    val state = _state.asStateFlow()

    init {
        onRefreshList()
        viewModelScope.launch {
            repository.articlesFlow.collect { list ->
                _state.value = _state.value.copy(articles = list)
            }
        }
    }

    fun onEventTriggered(event: NewsHomeEvent) {
        when(event) {
            is NewsHomeEvent.RefreshList -> {
                onRefreshList()
            }
            is NewsHomeEvent.CategoryChanged -> {
                onCategoryChanged(event.text)
            }
        }
    }

    private fun onCategoryChanged(text: String) = viewModelScope.launch {

    }

    private fun onRefreshList() = viewModelScope.launch {
        repository.refreshArticles()
    }

    sealed class NewsHomeEvent {
        object RefreshList: NewsHomeEvent()
        data class CategoryChanged(val text: String): NewsHomeEvent()
    }

    data class NewsHomeViewState(
        val articles: List<NewsArticle> = emptyList()
    )
}