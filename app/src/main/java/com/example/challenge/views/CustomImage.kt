package com.example.challenge.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun CustomImage(
    imageUrl: String,
    contentDescription: String?,
    size: Dp = 200.dp,
    clipShape: RoundedCornerShape = RoundedCornerShape(16.dp)
) {
    Image(
        painter = rememberAsyncImagePainter(imageUrl),
        contentDescription = contentDescription,
        modifier = Modifier
            .size(size)
            .clip(clipShape)
    )
}