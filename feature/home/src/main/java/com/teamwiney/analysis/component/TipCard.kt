package com.teamwiney.analysis.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun TipCard(
    modifier: Modifier = Modifier,
    thumbnail: String,
    title: String,
    onClick: () -> Unit = {},
) {
    val context = LocalContext.current

    Box(
        modifier = modifier
            .aspectRatio(1.1f)
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(thumbnail)
                .build(),
            modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(10.dp)),
            filterQuality = FilterQuality.Low,
            contentScale = ContentScale.Crop,
            contentDescription = "TIP_THUMBNAIL"
        )

        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color.Transparent, Color.Black
                        )
                    )
                )
                .clip(RoundedCornerShape(10.dp))
        )

        Text(
            text = title,
            style = WineyTheme.typography.captionB1,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = Color.White,
            modifier = Modifier
                .padding(13.dp, 8.dp)
                .align(Alignment.BottomStart)
        )
    }
}