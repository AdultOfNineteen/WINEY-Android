package com.teamwiney.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun ScoreSelector(
    modifier: Modifier = Modifier,
    value: Int,
    onValueChange: (Int) -> Unit,
    valueRange: IntRange = 1..2,
    inactiveColor: Color = WineyTheme.colors.gray_800,
    activeColor: Color = WineyTheme.colors.main_2
) {
    val range = valueRange.toList()

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(15.dp)
        ) {
            drawLine(
                color = inactiveColor,
                start = Offset(x = 0f, y = size.height / 2),
                end = Offset(x = size.width, y = size.height / 2),
                strokeWidth = 1.dp.toPx()
            )

            drawLine(
                color = activeColor,
                start = Offset(x = 0f, y = size.height / 2),
                end = Offset(x = size.width / (range.last() - 1) * (value - 1), y = size.height / 2),
                strokeWidth = 1.dp.toPx()
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for (i in range) {
                Spacer(
                    modifier = Modifier
                        .size(14.dp)
                        .background(
                            color = if (i > value) {
                                WineyTheme.colors.gray_800
                            } else {
                               WineyTheme.colors.main_2
                            },
                            shape = CircleShape
                        )
                        .clip(CircleShape)
                        .clickable {
                            onValueChange(i)
                        }
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewScoreSelector() {
    var score by remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WineyTheme.colors.background_1),
        contentAlignment = Alignment.Center
    ) {
        ScoreSelector(
            modifier = Modifier.padding(horizontal = 24.dp),
            value = score,
            valueRange = 1..5,
            onValueChange = { score = it }
        )
    }
}