package com.teamwiney.home.analysis.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.theme.WineyTheme
import kotlinx.coroutines.launch

@Composable
fun WineVarietyContent() {
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
                text = "선호 품종",
                style = WineyTheme.typography.title2,
                color = WineyTheme.colors.gray_50,
                textAlign = TextAlign.Center,
            )
        }
        HeightSpacer(height = 40.dp)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(start = 64.dp, end = 61.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.62f)
                    .aspectRatio(1f)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                WineyTheme.colors.main_1.copy(animatedProgress.value),
                                WineyTheme.colors.main_2.copy(animatedProgress.value),
                            )
                        )
                    )
            ) {
                Text(
                    text = "프리미티보\n74%",
                    style = WineyTheme.typography.bodyB1,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }

            Box(
                modifier = Modifier
                    .padding(top = 82.dp)
                    .align(Alignment.TopEnd)
                    .fillMaxWidth(0.52f)
                    .aspectRatio(1f)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF8E79D0).copy(animatedProgress.value),
                                Color.Transparent,
                            )
                        )
                    )
            ) {
                Text(
                    text = "프리미티보\n74%",
                    style = WineyTheme.typography.bodyB1,
                    color = WineyTheme.colors.gray_500,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }

            Box(
                modifier = Modifier
                    .padding(top = 182.dp, start = 40.dp)
                    .align(Alignment.TopStart)
                    .fillMaxWidth(0.52f)
                    .aspectRatio(1f)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF948FA6).copy(animatedProgress.value),
                                Color.Transparent,
                            )
                        )
                    )
            ) {
                Text(
                    text = "까르베네\n소비뇽",
                    style = WineyTheme.typography.bodyB1,
                    color = WineyTheme.colors.gray_600,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
    }
}