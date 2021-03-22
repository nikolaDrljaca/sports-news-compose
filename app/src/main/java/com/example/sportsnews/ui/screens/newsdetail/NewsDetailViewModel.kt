package com.example.sportsnews.ui.screens.newsdetail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.sportsnews.database.getDatabase
import com.example.sportsnews.domain.NewsArticle
import com.example.sportsnews.domain.emptyArticle
import com.example.sportsnews.repository.ArticlesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewsDetailViewModel(
    app: Application,
    savedStateHandle: SavedStateHandle
): AndroidViewModel(app) {
    private val repository = ArticlesRepository(getDatabase(app))

    private val url = savedStateHandle.get<String>("articleUrl")

    private val _state = MutableStateFlow(DetailViewState())
    val state = _state.asStateFlow()

    init {
        Log.e("Nikola", "DetailViewModel: $url", )
        getArticle()
    }

    private fun getArticle() = viewModelScope.launch {
        val article = repository.getArticleFromDatabase(url!!)
        _state.value = _state.value.copy(article = article)
    }

    data class DetailViewState(
        val article: NewsArticle = emptyArticle()
    )
}