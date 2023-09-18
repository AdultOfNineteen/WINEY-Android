package com.teamwiney.home.analysis.component.pagercontent

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.teamwiney.core.design.R
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.theme.WineyTheme
import kotlinx.coroutines.launch

@Composable
fun WinePriceContent() {
    val animatedProgress = remember { Animatable(0f) }

    LaunchedEffect(true) {
        launch {
            animatedProgress.snapTo(0f)
            animatedProgress.animateTo(1f, animationSpec = tween(1000))
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        HeightSpacer(height = 33.dp)

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(5.dp)
                    .background(WineyTheme.colors.main_2)
            )
            Text(
                text = "가격대",
                style = WineyTheme.typography.title2,
                color = WineyTheme.colors.gray_50,
                textAlign = TextAlign.Center,
            )
        }

        Box(
            modifier = Modifier.padding(horizontal = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.mipmap.img_splash_background),
                contentDescription = "IMG_SPLASH_BACKGROUND",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Inside
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "평균 구매가",
                    style = WineyTheme.typography.captionB1,
                    color = WineyTheme.colors.gray_600
                )

                Text(
                    text = "70,580 원",
                    style = WineyTheme.typography.title1,
                    color = WineyTheme.colors.gray_50.copy(animatedProgress.value)
                )
            }
        }
    }
}