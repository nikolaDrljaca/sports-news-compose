package com.example.sportsnews.ui.screens.newshome

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.sportsnews.ui.screens.newshome.NewsCategories.*
import com.example.sportsnews.ui.theme.blandOrange
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.example.sportsnews.domain.NewsArticle
import com.example.sportsnews.network.NetworkArticle
import com.example.sportsnews.ui.reusable.SimpleCoilImage
import com.example.sportsnews.ui.screens.newsdetail.ErrorLoadingArticleList
import com.example.sportsnews.ui.screens.newsdetail.LoadingArticleList
import kotlinx.coroutines.flow.Flow
import java.util.*

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(24.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Browse News",
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.onSurface
        )
        SimpleIconButton(imageVector = Icons.Rounded.Search) { }
    }
}

@Composable
fun SimpleIconButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    backgroundColor: Color = MaterialTheme.colors.secondary,
    onClick: () -> Unit,
) {
    val shape = RoundedCornerShape(percent = 30)
    Surface(
        shape = shape,
        modifier = modifier
            .clip(shape = shape)
            .clickable { onClick() },
        color = backgroundColor,
        contentColor = MaterialTheme.colors.onSurface
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            modifier = Modifier.padding(12.dp)
        )
    }
}

@Composable
fun SimpleIconButton(
    modifier: Modifier = Modifier,
    painter: Painter,
    backgroundColor: Color = MaterialTheme.colors.secondary,
    onClick: () -> Unit,
) {
    val shape = RoundedCornerShape(percent = 30)
    Surface(
        shape = shape,
        modifier = modifier
            .clip(shape = shape)
            .clickable { onClick() },
        color = backgroundColor,
        contentColor = MaterialTheme.colors.onSurface
    ) {
        Icon(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.padding(12.dp)
        )
    }
}

@Composable
fun SportsArticleList(
    modifier: Modifier = Modifier,
    articles: List<NewsArticle>,
    onClick: (String) -> Unit
) {
    val listState = rememberLazyListState()
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.75f),
        state = listState,
        horizontalArrangement = Arrangement.Center
    ) {
        item {
             if (articles.isNotEmpty()) Spacer(modifier = Modifier.padding(32.dp))
        }

        itemsIndexed(articles) { index, article ->
            val listOfItemsVisible = listState.layoutInfo.visibleItemsInfo
            val indexes = arrayListOf<Int>()
            for (item in listOfItemsVisible) {
                indexes.add(item.index)
            }
            val isVisible = indexes.contains(index)
            SportsArticleItem(article = article, isVisible = isVisible) { onClick(it) }
        }
    }
}

@Composable
fun SportsArticleItem(
    modifier: Modifier = Modifier,
    article: NewsArticle,
    isVisible: Boolean,
    onClick: (String) -> Unit
) {
    val shape = RoundedCornerShape(32.dp)
    val scale by animateFloatAsState(if (isVisible) 1f else 0.8f)
    val alpha by animateFloatAsState(if (isVisible) 1f else 0.3f)
    Surface(
        elevation = 4.dp,
        shape = shape,
        modifier = modifier
            .width(270.dp)
            .height(500.dp)
            .padding(horizontal = 12.dp)
            .clip(shape)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .alpha(alpha)
            .clickable { onClick(article.url) }
    ) {
        Box {
            SimpleCoilImage(imageLink = article.urlToImage)

            BottomArticleText(
                modifier = Modifier.align(Alignment.BottomCenter),
                date = article.publishedAt,
                title = article.title,
            )
        }
    }

}


@Composable
fun BottomArticleText(
    modifier: Modifier = Modifier,
    date: String,
    title: String
) {
    val colorsForGradient = listOf(Color.Transparent, Color.Black)
    val bottomGradient = Brush.linearGradient(
        colors = colorsForGradient,
        start = Offset(x = 0f, y = 0f),
        end = Offset(x = 0f, y = 500f)
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(bottomGradient)
            .padding(24.dp),
    ) {
        Text(
            text = date,
            style = MaterialTheme.typography.caption,
            color = blandOrange,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.h6,
            color = Color.White,
            maxLines = 5
        )
    }
}

/*
    Code for category chips at the bottom
 */

private sealed class NewsCategories(val title: String) {
    object Business : NewsCategories("business")
    object Sports : NewsCategories("sports")
    object General : NewsCategories("general")
    object Science : NewsCategories("science")
    object Health : NewsCategories("health")
}

private val categories = listOf(
    Business,
    Sports,
    General,
    Science,
    Health
)


@Composable
fun CategoryRow(
    modifier: Modifier = Modifier,
    onCategoryClicked: (String) -> Unit,
) {
    var currentSelected by remember { mutableStateOf<NewsCategories>(Sports) }
    LazyRow(
        modifier = modifier
    ) {
        item {
            Spacer(modifier = Modifier.width(16.dp))
        }
        items(categories) { item ->
            CategoryChip(
                text = item.title.capitalize(Locale.ROOT),
                isSelected = currentSelected.title == item.title,
                onClick = {
                    currentSelected = item
                    onCategoryClicked(item.title)
                }
            )
        }
    }
}


@Composable
fun CategoryChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val shape = RoundedCornerShape(percent = 25)
    val surfaceColor by animateColorAsState(if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.secondary)
    val textColor by animateColorAsState(if (isSelected) Color.White else Color.Gray)

    //animate color changes
    Surface(
        color = surfaceColor,
        shape = shape,
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .clip(shape)
            .clickable { onClick() }
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Medium),
            modifier = Modifier.padding(16.dp),
            color = textColor
        )
    }
}