package com.teamwiney.notewrite.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.theme.WineyTheme

@Composable
@Preview
fun WineTasetSlider(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp),
    title: String = "당도",
    subTitle: String = "단맛의 정도"
) {

    var level by remember {
        mutableStateOf<Int?>(null)
    }

    Column(modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = WineyTheme.typography.bodyB1,
                color = WineyTheme.colors.gray_500
            )
            Text(
                text = "・",
                style = WineyTheme.typography.captionB1,
                color = WineyTheme.colors.gray_50
            )
            Text(
                text = subTitle,
                style = WineyTheme.typography.captionB1,
                color = WineyTheme.colors.gray_600
            )
        }
        HeightSpacer(height = 15.dp)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "낮음",
                style = WineyTheme.typography.captionM1,
                color = WineyTheme.colors.gray_600
            )
            Text(
                text = "높음",
                style = WineyTheme.typography.captionM1,
                color = WineyTheme.colors.gray_600
            )
        }
        HeightSpacer(height = 10.dp)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(14.dp)
        ) {
            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                val widthUnit = size.width / 4
                drawLine(
                    color = Color(0xFF63666A),
                    start = Offset(0f, size.height / 2),
                    end = Offset(size.width, size.height / 2),
                    strokeWidth = 2.dp.toPx()
                )
                drawLine(
                    color = Color(0xFF774BFF),
                    start = Offset(0f, size.height / 2),
                    end = Offset(widthUnit * (level ?: 0), size.height / 2),
                    strokeWidth = 2.dp.toPx()
                )
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .clip(CircleShape)
                        .background(
                            color = if ((level
                                    ?: 0) >= 0
                            ) WineyTheme.colors.main_2 else WineyTheme.colors.gray_600
                        )
                        .clickable {
                            level = 0
                        }
                )
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .clip(CircleShape)
                        .background(
                            color = if ((level
                                    ?: 0) >= 1
                            ) WineyTheme.colors.main_2 else WineyTheme.colors.gray_600
                        )
                        .clickable {
                            level = 1
                        }
                )
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .clip(CircleShape)
                        .background(
                            color = if ((level
                                    ?: 0) >= 2
                            ) WineyTheme.colors.main_2 else WineyTheme.colors.gray_600
                        )
                        .clickable {
                            level = 2
                        }
                )
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .clip(CircleShape)
                        .background(
                            color = if ((level
                                    ?: 0) >= 3
                            ) WineyTheme.colors.main_2 else WineyTheme.colors.gray_600
                        )
                        .clickable {
                            level = 3
                        }
                )
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .clip(CircleShape)
                        .background(
                            color = if ((level
                                    ?: 0) >= 4
                            ) WineyTheme.colors.main_2 else WineyTheme.colors.gray_600
                        )
                        .clickable {
                            level = 4
                        }
                )
            }

        }
    }

}