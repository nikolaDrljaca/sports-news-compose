package com.example.sportsnews.ui.screens.newsdetail

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoadingArticleList() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(strokeWidth = 6.dp, modifier = Modifier.size(40.dp))
    }
}

@Composable
fun ErrorLoadingArticleList(
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = "An error has occurred.",
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.error
        )
        
        TextButton(onClick = onClick) {
            Text(text = "Try again")
        }
    }
}