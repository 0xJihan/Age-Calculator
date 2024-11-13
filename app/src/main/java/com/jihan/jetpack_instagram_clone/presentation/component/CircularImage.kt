package com.jihan.jetpack_instagram_clone.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.jihan.jetpack_instagram_clone.R


@Composable
fun CircularImage(
    modifier: Modifier = Modifier,
    painter: AsyncImagePainter = rememberAsyncImagePainter(R.drawable.person),
    clickable: Boolean = true,
    onClick: () -> Unit = {},
) {


    val mod = if (clickable) modifier.clickable { onClick() } else modifier


    Image(
        modifier = mod
            .clip(CircleShape),
        painter = painter,
        contentScale = ContentScale.Crop,
        contentDescription = null,
    )


}