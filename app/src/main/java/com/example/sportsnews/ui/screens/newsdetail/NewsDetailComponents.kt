package com.example.sportsnews.ui.screens.newsdetail

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sportsnews.R
import com.example.sportsnews.domain.NewsArticle
import com.example.sportsnews.network.NetworkArticle
import com.example.sportsnews.ui.reusable.SimpleCoilImage
import com.example.sportsnews.ui.reusable.SimpleTextButton
import com.example.sportsnews.ui.screens.newshome.SimpleIconButton
import com.example.sportsnews.ui.theme.blandOrange

@Composable
fun DetailHeader(
    modifier: Modifier = Modifier,
    article: NewsArticle,
    onBackClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        ArticleImageWithText(
            date = article.publishedAt,
            title = article.title,
            imageLink = article.urlToImage,
            onBackClick = onBackClick
        )

        ArticleDetails(
            description = article.description,
            content = article.content,
            articleUrl = article.url
        )
    }
}

@Composable
private fun ArticleImageWithText(
    date: String,
    title: String,
    imageLink: String,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {
        SimpleCoilImage(imageLink = imageLink)
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            TopAppBarDetail(onBackClick = { onBackClick() }, onBookmarkClick = { /*TODO*/ })
            DetailArticleBottomText(date = date, title = title)
        }
    }
}

@Composable
private fun ArticleDetails(
    description: String,
    content: String,
    articleUrl: String = ""
) {
    val context = LocalContext.current
    Surface(
        modifier = Modifier.fillMaxSize(),
        shape = RoundedCornerShape(topStartPercent = 10, topEndPercent = 10),
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = description,
                style = MaterialTheme.typography.body1.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier.padding(bottom = 12.dp),
                maxLines = 4,
            )
            Text(
                text = content,
                style = MaterialTheme.typography.body2,
                overflow = TextOverflow.Ellipsis,
                maxLines = 5
            )

            SimpleTextButton(
                modifier = Modifier.padding(top = 24.dp),
                text = "Read full article"
            ) {
                val page = Uri.parse(articleUrl)
                val intent = Intent(Intent.ACTION_VIEW, page)
                context.startActivity(intent)
            }
        }
    }
}

@Composable
private fun DetailArticleBottomText(
    modifier: Modifier = Modifier,
    date: String,
    title: String
) {
    val colorsForGradient = listOf(Color.Transparent, Color.Black)
    val bottomGradient = Brush.linearGradient(
        colors = colorsForGradient,
        start = Offset(x = 0f, y = 0f),
        end = Offset(x = 0f, y = 400f)
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(bottomGradient)
            .padding(start = 24.dp, end = 24.dp, bottom = 12.dp),
    ) {
        Text(
            text = date,
            style = MaterialTheme.typography.caption,
            color = blandOrange,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.h4,
            color = Color.White,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun TopAppBarDetail(
    onBackClick: () -> Unit,
    onBookmarkClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, top = 40.dp, bottom = 40.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SimpleIconButton(imageVector = Icons.Rounded.ArrowBack) { onBackClick() }

        SimpleIconButton(
            painter = painterResource(id = R.drawable.ic_baseline_bookmark_border_24),
            backgroundColor = MaterialTheme.colors.primary
        ) { onBookmarkClick() }
    }
}