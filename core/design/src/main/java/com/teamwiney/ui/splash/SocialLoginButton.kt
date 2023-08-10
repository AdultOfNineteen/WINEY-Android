package com.teamwiney.ui.splash

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun SocialLoginButton(
    @DrawableRes drawable: Int,
    onClick: () -> Unit,
) {
    Image(
        painter = painterResource(id = drawable),
        contentDescription = null,
        modifier = Modifier
            .size(56.dp)
            .clip(CircleShape)
            .clickable {
                onClick()
            },
        contentScale = ContentScale.Crop
    )
}