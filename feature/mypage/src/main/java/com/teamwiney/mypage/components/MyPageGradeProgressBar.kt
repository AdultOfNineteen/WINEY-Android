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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import com.teamwiney.ui.theme.WineyTheme

data class Grade(
    val name: String,
    val count: Int
)

private val gradeData = listOf(
    Grade("GLASS", 0),
    Grade("BOTTLE", 3),
    Grade("OAK", 7),
    Grade("WINERY", 12)
)

@Preview
@Composable
fun MyPageGradeProgressBar(
    modifier: Modifier = Modifier,
    noteCount: Int = 7,
    grade: List<Grade> = gradeData,
    inactiveColor: Color = WineyTheme.colors.gray_800,
    activeColor: Color = WineyTheme.colors.main_2,
    textStyle: TextStyle = WineyTheme.typography.captionM2.copy(
        color = inactiveColor
    )
) {
    val maxCount: Int = grade.maxOfOrNull { it.count } ?: 0

    val textMeasurer = rememberTextMeasurer()
    val textMeasureResults = remember(grade) {
        grade.map { grade ->
            textMeasurer.measure(
                text = grade.name,
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
            val height = size.height

            drawLine(
                color = inactiveColor,
                start = Offset(x = 0f, y = 7.dp.toPx()),
                end = Offset(x = size.width, y = 7.dp.toPx()),
                strokeWidth = 1.dp.toPx()
            )

            for (i in grade.indices) {
                drawCircle(
                    color = inactiveColor,
                    radius = 7.dp.toPx(),
                    center = Offset(
                        x = width * (grade[i].count / maxCount.toFloat()),
                        y = 7.dp.toPx()
                    )
                )

                val textSize = textMeasureResults[i].size
                val textCenter = textSize.center

                drawText(
                    textLayoutResult = textMeasureResults[i],
                    topLeft = Offset(
                        x = width * (grade[i].count / maxCount.toFloat()) - textCenter.x,
                        y = 21.dp.toPx()
                    )
                )
            }

            drawCircle(
                color = activeColor,
                radius = 7.dp.toPx(),
                center = Offset(
                    x = width * (noteCount / maxCount.toFloat()),
                    y = 7.dp.toPx()
                )
            )
        }
    }
}