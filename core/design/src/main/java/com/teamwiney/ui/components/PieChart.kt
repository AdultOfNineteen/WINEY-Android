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
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.center
import com.teamwiney.ui.theme.WineyTheme
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
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
    backgroundColor: Color = WineyTheme.colors.background_1,
    progress: Float = 1f,
    data: List<ChartData>
) {
    val animatedAngle = -90f + (360 * progress)
    val textMeasurer = rememberTextMeasurer()
    val textMeasureResults = remember(data) {
        data.mapIndexed { index, chartData ->
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

            val radius = width / 2.5f
            val strokeWidth = radius * .5f
            val gapAngle = 4f // Arc 사이의 간격 각도

            var startAngle = -90f

            for (index in 0..data.lastIndex) {

                val chartData = data[index]
                val sweepAngle = chartData.value.asAngle

                val textMeasureResult = textMeasureResults[index]

                val arcSize = width - strokeWidth

                if (index == 0) {
                    // 첫 번째 Arc일 때 바깥쪽만 두꺼워지도록 Stroke의 두께를 조절
                    drawArc(
                        color = chartData.color,
                        startAngle = startAngle,
                        sweepAngle = min(
                            if (data.lastIndex == 0) sweepAngle else (sweepAngle - gapAngle),
                            max((animatedAngle - startAngle), 0f)
                        ),
                        useCenter = false,
                        topLeft = Offset(width - arcSize / 2f, height - arcSize / 2f),
                        size = Size(arcSize, arcSize),
                        style = Stroke(strokeWidth * 1.3f)
                    )
                } else {
                    drawArc(
                        color = chartData.color,
                        startAngle = startAngle,
                        sweepAngle = min((sweepAngle - gapAngle), max((animatedAngle - startAngle), 0f)),
                        useCenter = false,
                        topLeft = Offset(width - arcSize / 2f, height - arcSize / 2f),
                        size = Size(arcSize, arcSize),
                        style = Stroke(strokeWidth)
                    )
                }

                drawCircle(
                    color = backgroundColor,
                    center = Offset(
                        x = size.width / 2f,
                        y = size.height / 2f
                    ),
                    radius = (width - strokeWidth) / 2.35f
                )

                drawLabel(
                    textMeasureResult = textMeasureResult,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    chartRadius = width.toFloat(),
                    color = chartData.color
                )

                startAngle += sweepAngle
            }
        }
    }
}

private fun DrawScope.drawLabel(
    textMeasureResult: TextLayoutResult,
    startAngle: Float,
    sweepAngle: Float,
    chartRadius: Float,
    color: Color
) {
    val textSize = textMeasureResult.size
    val textCenter = textSize.center

    val angleInRadians = (startAngle + sweepAngle / 2).degreeToAngle

    val legendRadius = chartRadius / 2f + textSize.width
    val legendX = center.x + legendRadius * cos(angleInRadians)
    val legendY = center.y + legendRadius * sin(angleInRadians)

    drawCircle(
        color = color,
        radius = 10f,
        center = Offset(legendX - textCenter.x - 25f, legendY - textCenter.y + 10f)
    )

    drawText(
        textLayoutResult = textMeasureResult,
        topLeft = Offset(
            legendX - textCenter.x,
            legendY - textCenter.y
        )
    )
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
                data = listOf(
                    ChartData(
                        label = "화이트",
                        color = WineyTheme.colors.main_1,
                        value = 100f,
                        textStyle = WineyTheme.typography.title2.copy(
                            color = WineyTheme.colors.gray_50
                        )
                    )
                )
            )
        }
    }
}

@Preview
@Composable
fun PreviewPieChart2() {
    WineyTheme {
        Box(
            modifier = Modifier.fillMaxSize().background(WineyTheme.colors.background_1),
            contentAlignment = Alignment.Center
        ) {
            PieChart(
                data = listOf(
                    ChartData(
                        label = "레드",
                        color = WineyTheme.colors.main_1,
                        value = 75f,
                        textStyle = WineyTheme.typography.title2.copy(
                            color = WineyTheme.colors.gray_50
                        )
                    ),
                    ChartData(
                        label = "화이트",
                        color = WineyTheme.colors.gray_800,
                        value = 18f,
                        textStyle = WineyTheme.typography.bodyB2.copy(
                            color = WineyTheme.colors.gray_800
                        )
                    ),
                    ChartData(
                        label = "로제",
                        color = WineyTheme.colors.gray_900,
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