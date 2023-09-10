package com.teamwiney.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import com.teamwiney.ui.theme.WineyTheme
import kotlin.math.cos
import kotlin.math.sin

@Preview
@Composable
private fun PieChart(
    chartDataList: List<ChartData> = listOf(
        ChartData("레드", Color(0xFF5123DF), 75f),
        ChartData("화이트", Color(0xFF5E489B), 18f),
        ChartData("로제", WineyTheme.colors.gray_800, 7f)
    ),
    textStyle: TextStyle = WineyTheme.typography.title2
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WineyTheme.colors.background_1),
        contentAlignment = Alignment.Center
    ) {
        val textMeasurer = rememberTextMeasurer()
        val textMeasureResults = remember(chartDataList) {
            chartDataList.mapIndexed { index, chartData ->
                val labelText = if (index == 0) {
                    "${chartData.label}\n${chartData.data}%"
                } else {
                    chartData.label
                }
                textMeasurer.measure(
                    text = labelText,
                    style = textStyle
                )
            }
        }

        Canvas(
            modifier = Modifier.size(280.dp)
        ) {
            val width = size.width
            val radius = width / 3f
            val strokeWidth = radius * .4f
            val gapAngle = 2f // Arc 사이의 간격 각도

            var startAngle = -90f

            for (index in 0..chartDataList.lastIndex) {

                val chartData = chartDataList[index]
                val sweepAngle = chartData.data.asAngle
                val angleInRadians = (startAngle + sweepAngle / 2).degreeToAngle
                val textMeasureResult = textMeasureResults[index]
                val textSize = textMeasureResult.size

                if (index == 0) {
                    // 첫 번째 Arc일 때 바깥쪽만 두꺼워지도록 Stroke의 두께를 조절합니다.
                    drawArc(
                        color = chartData.color,
                        startAngle = startAngle,
                        sweepAngle = sweepAngle - gapAngle,
                        useCenter = false,
                        topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
                        size = Size(width - strokeWidth, width - strokeWidth),
                        style = Stroke(strokeWidth * 1.2f) // 두께를 조절합니다.
                    )
                } else {
                    drawArc(
                        color = chartData.color,
                        startAngle = startAngle,
                        sweepAngle = sweepAngle - gapAngle,
                        useCenter = false,
                        topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
                        size = Size(width - strokeWidth, width - strokeWidth),
                        style = Stroke(strokeWidth)
                    )
                }

                val textCenter = textSize.center

                val legendRadius = width / 2f + textCenter.x + 60f
                val legendX = center.x + legendRadius * cos(angleInRadians)
                val legendY = center.y + legendRadius * sin(angleInRadians)

                drawCircle(
                    color = chartData.color,
                    radius = 10f, // 점의 반지름을 조절합니다.
                    center = Offset(legendX - textSize.width / 1.5f, legendY - textSize.height / 4f)
                )

                drawText(
                    textLayoutResult = textMeasureResult,
                    color = Color.Gray,
                    topLeft = Offset(
                        legendX - textCenter.x,
                        legendY - textCenter.y
                    )
                )

                startAngle += sweepAngle
            }
        }
    }
}

private val Float.degreeToAngle
    get() = (this * Math.PI / 180f).toFloat()

private val Float.asAngle: Float
    get() = this * 360f / 100f

data class ChartData(val label: String, val color: Color, val data: Float)