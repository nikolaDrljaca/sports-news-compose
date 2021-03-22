package com.example.sportsnews.ui.screens.newshome

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.sportsnews.domain.NewsArticle
import com.example.sportsnews.network.NetworkArticle
import com.example.sportsnews.ui.screens.newshome.NewsHomeViewModel.*
import kotlinx.coroutines.flow.Flow

@Composable
fun NewsHome(
    onArticleClicked: (String) -> Unit,
) {
    val newsHomeViewModel: NewsHomeViewModel = viewModel()
    val state by newsHomeViewModel.state.collectAsState()

    //

    NewsHomeScreen(
        articles = state.articles,
        onArticleClicked = { onArticleClicked(it) },
        onCategoryClicked = { category ->
            newsHomeViewModel.onEventTriggered(NewsHomeEvent.CategoryChanged(category))
        }
    )
}

@Composable
fun NewsHomeScreen(
    articles: List<NewsArticle>,
    onArticleClicked: (String) -> Unit,
    onCategoryClicked: (String) -> Unit
) {
    Scaffold(
        topBar = { TopAppBar() },
        backgroundColor = MaterialTheme.colors.background
    ) {
        Column(modifier = Modifier) {
            SportsArticleList(
                articles = articles,
                onClick = { onArticleClicked(it) }
            )
            CategoryRow(
                modifier = Modifier.padding(vertical = 24.dp),
                onCategoryClicked = {  }
            )
        }
    }
}