package com.teamwiney.mypage.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import com.teamwiney.data.network.model.response.WineGradeStandard
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun MyPageGradeProgressBar(
    modifier: Modifier = Modifier,
    noteCount: Int,
    gradeData: List<WineGradeStandard>,
    inactiveColor: Color = WineyTheme.colors.gray_800,
    activeColor: Color = WineyTheme.colors.main_2,
    textStyle: TextStyle = WineyTheme.typography.captionM2.copy(
        color = inactiveColor
    )
) {
    val maxValue: Int = gradeData.maxOfOrNull { it.minCount } ?: 0

    val textMeasurer = rememberTextMeasurer()
    val textMeasureResults = remember(gradeData) {
        gradeData.map { grade ->
            textMeasurer.measure(
                text = grade.name.name,
                style = textStyle
            )
        }
    }

    Box(
        modifier = modifier.padding(horizontal = 24.dp)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp)
                .height(32.dp)
        ) {
            val width = size.width

            drawLine(
                color = inactiveColor,
                start = Offset(x = 0f, y = 7.dp.toPx()),
                end = Offset(x = size.width, y = 7.dp.toPx()),
                strokeWidth = 1.dp.toPx()
            )

            for (i in gradeData.indices) {
                val progress = gradeData[i].minCount / maxValue.toFloat()

                drawCircle(
                    color = inactiveColor,
                    radius = 7.dp.toPx(),
                    center = Offset(
                        x = width * progress,
                        y = 7.dp.toPx()
                    )
                )

                val textSize = textMeasureResults[i].size
                val textCenter = textSize.center

                drawText(
                    textLayoutResult = textMeasureResults[i],
                    topLeft = Offset(
                        x = width * progress - textCenter.x,
                        y = 21.dp.toPx()
                    )
                )
            }

            drawCircle(
                color = activeColor,
                radius = 7.dp.toPx(),
                center = Offset(
                    x = width * minOf(noteCount / maxValue.toFloat(), 1f),
                    y = 7.dp.toPx()
                )
            )
        }
    }
}