package com.teamwiney.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.teamwiney.ui.theme.WineyTheme
import kotlin.math.cos
import kotlin.math.sin

data class ChartData(
    val label: String,
    val color: Color,
    val value: Float,
    val textStyle: TextStyle
)

@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    chartDataList: List<ChartData>
) {

    val textMeasurer = rememberTextMeasurer()
    val textMeasureResults = remember(chartDataList) {
        chartDataList.mapIndexed { index, chartData ->
            val labelText = if (index == 0) {
                "${chartData.label}\n${chartData.value}%"
            } else {
                chartData.label
            }
            textMeasurer.measure(
                text = labelText,
                style = chartData.textStyle
            )
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width / 2
            val height = size.height / 2

            val radius = width / 3f
            val strokeWidth = radius * .4f
            val gapAngle = 2f // Arc 사이의 간격 각도

            var startAngle = -90f

            for (index in 0..chartDataList.lastIndex) {

                val chartData = chartDataList[index]
                val sweepAngle = chartData.value.asAngle
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
                        topLeft = Offset(width / 1.75f, height / 1.75f),
                        size = Size(width - strokeWidth, width - strokeWidth),
                        style = Stroke(strokeWidth * 1.2f) // 두께를 조절합니다.
                    )
                } else {
                    drawArc(
                        color = chartData.color,
                        startAngle = startAngle,
                        sweepAngle = sweepAngle - gapAngle,
                        useCenter = false,
                        topLeft = Offset(width / 1.75f, height / 1.75f),
                        size = Size(width - strokeWidth, width - strokeWidth),
                        style = Stroke(strokeWidth)
                    )
                }

                val textCenter = textSize.center

                val legendRadius = if (startAngle >= 235f) {
                    width / 2f + textSize.width
                } else if (startAngle >= 180f && startAngle + sweepAngle >= 235f) {
                    width / 2f + textSize.width + 20f
                } else {
                    width / 2f + textCenter.x + 30f
                }

                val legendX = center.x + legendRadius * cos(angleInRadians) + 65f
                val legendY = center.y + legendRadius * sin(angleInRadians)

                drawCircle(
                    color = chartData.color,
                    radius = 10f,
                    center = Offset(legendX - textCenter.x - 20f, legendY - textCenter.y + 10f)
                )

                drawText(
                    textLayoutResult = textMeasureResult,
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

@Preview
@Composable
fun PreviewPieChart() {
    WineyTheme {
        Box(
            modifier = Modifier.fillMaxSize().background(WineyTheme.colors.background_1),
            contentAlignment = Alignment.Center
        ) {
            PieChart(
                chartDataList = listOf(
                    ChartData(
                        label = "레드",
                        color = Color(0xFF5123DF),
                        value = 75f,
                        textStyle = WineyTheme.typography.title2.copy(
                            color = WineyTheme.colors.gray_50
                        )
                    ),
                    ChartData(
                        label = "화이트",
                        color = Color(0xFF5E489B),
                        value = 18f,
                        textStyle = WineyTheme.typography.bodyB2.copy(
                            color = WineyTheme.colors.gray_800
                        )
                    ),
                    ChartData(
                        label = "로제",
                        color = WineyTheme.colors.gray_800,
                        value = 7f,
                        textStyle = WineyTheme.typography.captionB1.copy(
                            color = WineyTheme.colors.gray_900
                        )
                    )
                )
            )
        }
    }
}