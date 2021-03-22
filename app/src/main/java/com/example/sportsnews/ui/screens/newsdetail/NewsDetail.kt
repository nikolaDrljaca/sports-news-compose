package com.example.sportsnews.ui.screens.newsdetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sportsnews.domain.NewsArticle
import androidx.compose.runtime.getValue
import com.example.sportsnews.network.NetworkArticle

@Composable
fun NewsDetail(
    onBackPressed: () -> Unit,
) {
    val detailViewModel: NewsDetailViewModel = viewModel()
    val state by detailViewModel.state.collectAsState()
    NewsDetailScreen(
        article = state.article,
        onBackPressed = onBackPressed
    )
}

@Composable
private fun NewsDetailScreen(
    article: NewsArticle,
    onBackPressed: () -> Unit,
) {
    DetailHeader(
        article = article,
        onBackClick = onBackPressed
    )
}