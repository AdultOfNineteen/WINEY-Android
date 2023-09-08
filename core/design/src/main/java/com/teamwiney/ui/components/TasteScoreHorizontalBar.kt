package com.teamwiney.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.teamwiney.ui.theme.WineyTheme
import kotlin.math.max

@Composable
fun TasteScoreHorizontalBar(
    label: String,
    peopleScore: Int,
    defaultScore: Int
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

        var barWidth by remember { mutableIntStateOf(0) }
        val density = LocalDensity.current

        Row(
            modifier = Modifier.width(density.run { barWidth.toDp() } + 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = WineyTheme.typography.bodyB2,
                color = WineyTheme.colors.gray_50,
            )

            ScoreIndicator(score = max(peopleScore, defaultScore))
        }
        Box(modifier = Modifier.fillMaxWidth(0.9f)) {
            if (peopleScore < defaultScore) {
                RoundedHorizontalBar(
                    modifier = Modifier.onGloballyPositioned { coordinates ->
                        barWidth = coordinates.size.width
                    },
                    targetProgress = defaultScore / 5f,
                    color = WineyTheme.colors.point_1
                )
                RoundedHorizontalBar(
                    targetProgress = peopleScore / 5f,
                    color = WineyTheme.colors.main_2
                )
            } else {
                RoundedHorizontalBar(
                    modifier = Modifier.onGloballyPositioned { coordinates ->
                        barWidth = coordinates.size.width
                    },
                    targetProgress = peopleScore / 5f,
                    color = WineyTheme.colors.main_2
                )
                RoundedHorizontalBar(
                    targetProgress = defaultScore / 5f,
                    color = WineyTheme.colors.point_1
                )
            }
        }
    }
}

@Composable
fun RoundedHorizontalBar(
    /*@FloatRange(from = 0.0, to = 1.0)*/
    targetProgress: Float,
    modifier: Modifier = Modifier,
    color: Color,
    height: Dp = 9.dp
) {
    val progress = remember { Animatable(0f) }

    LaunchedEffect(targetProgress) {
        progress.animateTo(targetProgress, animationSpec = tween(1000))
    }

    Box(
        modifier = modifier
            .fillMaxWidth(progress.value)
            .height(height)
            .background(color = color, shape = CircleShape)
    )
}

@Composable
private fun ScoreIndicator(
    modifier: Modifier = Modifier,
    score: Int
) {
    Box(
        modifier = modifier
            .width(22.dp)
            .height(29.dp)
    ) {
        // TODO : 배경 이미지 추가

        Text(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 2.dp, bottom = 8.dp, start = 7.dp, end = 7.dp),
            text = "$score",
            style = WineyTheme.typography.bodyB2,
            color = WineyTheme.colors.gray_50
        )
    }
}