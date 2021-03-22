package com.example.sportsnews.ui.reusable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.sportsnews.R
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun SimpleCoilImage(
    imageLink: String
) {
    CoilImage(
        modifier = Modifier.fillMaxSize(),
        data = imageLink,
        contentDescription = null,
        fadeIn = true,
        contentScale = ContentScale.Crop,
        loading = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.size(36.dp))
            }
        },
        error = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_round_broken_image_24),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )
            }
        }
    )
}

@Composable
fun SimpleTextButton(
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color = MaterialTheme.colors.primary,
    onClick: () -> Unit,
) {
    val shape = RoundedCornerShape(percent = 30)
    Surface(
        shape = shape,
        modifier = modifier
            .clip(shape = shape)
            .clickable { onClick() },
        color = backgroundColor,
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.button
        )
    }
}

