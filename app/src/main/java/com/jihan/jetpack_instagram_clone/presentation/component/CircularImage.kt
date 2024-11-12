package com.jihan.jetpack_instagram_clone.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import com.jihan.jetpack_instagram_clone.R


@Composable
fun CircularImage(
    modifier: Modifier = Modifier,
    model: Any? = R.drawable.person,
    clickable: Boolean = true,
    onClick: () -> Unit = {},
) {

    val mod = if (clickable) modifier.clickable { onClick() } else modifier


    AsyncImage(
        modifier = mod
            .clip(CircleShape),
        model = model,
        contentScale = ContentScale.Crop,
        contentDescription = null,
    )


}