package com.teamwiney.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwiney.core.design.R
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun TipCard(
    modifier: Modifier = Modifier,
    image: String = "",
    title: String
) {
    Box(
        modifier = modifier
            .aspectRatio(1.1f)
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_dummy_wine),
            contentDescription = "IMG_DUMMY_WINE",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(10.dp))
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

@Preview
@Composable
fun PreviewTipCard() {
    WineyTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            TipCard(
                modifier = Modifier.width(155.dp),
                title = "와인이 처음이여서 뭘 마셔야 할지 모르겠다면?"
            )
        }
    }
}