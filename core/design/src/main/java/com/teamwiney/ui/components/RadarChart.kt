package com.teamwiney.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import com.teamwiney.ui.theme.WineyTheme
import kotlin.math.ceil
import kotlin.math.cos
import kotlin.math.sin

data class RadarData(val label: String, val value: Float)

@Composable
fun RadarChart(
    modifier: Modifier = Modifier,
    progress: Float = 1f,
    data: List<RadarData>,
    contentColor: Color = WineyTheme.colors.main_3.copy(alpha = 0.4f),
    strokeColor: Color = WineyTheme.colors.gray_50.copy(alpha = 0.4f),
    strokeWidth: Dp = 1.dp,
    maxValue: Float = 5f,
    textStyle: TextStyle = WineyTheme.typography.captionB1.copy(
        color = WineyTheme.colors.gray_600
    )
) {
    val textMeasurer = rememberTextMeasurer()
    val textMeasureResults = remember(data) {
        data.mapIndexed { index, chartData ->
            val labelStyle = if (index == 0) {
                textStyle.copy(color = Color.White)
            } else {
                textStyle
            }

            textMeasurer.measure(
                text = chartData.label,
                style = labelStyle
            )
        }
    }
    

    Box(modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRadarChart(
                data,
                contentColor,
                strokeColor,
                strokeWidth,
                maxValue,
                progress
            )
            drawLabels(textMeasureResults, data.map { it.label })
        }
    }
}

private fun DrawScope.drawLabels(textMeasureResults: List<TextLayoutResult>, labels: List<String>) {
    val centerX = size.width / 2
    val centerY = size.height / 2
    val radius = (size.width / 2) * 0.95
    val angleStep = (2 * Math.PI / labels.size).toFloat()

    for (i in labels.indices) {
        // (-2 * Math.PI / 4) 를 해주는 이유 : canvas에 그릴때 90도부터 시작하는데 이를 0도 부터 시작하기 위함
        val angle = angleStep * i + (-2 * Math.PI / 4).toFloat()
        val labelX = centerX + radius * cos(angle)
        val labelY = centerY + radius * sin(angle)

        val textMeasureResult = textMeasureResults[i]
        val textSize = textMeasureResult.size

        drawText(
            textLayoutResult = textMeasureResults[i],
            topLeft = Offset(
                labelX.toFloat() - textSize.center.x,
                labelY.toFloat() - textSize.center.y
            )
        )
    }
}

private fun DrawScope.drawRadarChart(
    data: List<RadarData>,
    contentColor: Color,
    strokeColor: Color,
    strokeWidth: Dp,
    maxValue: Float,
    animatedProgress: Float
) {
    val centerX = size.width / 2
    val centerY = size.height / 2
    val radius = (size.width / 2) * 0.8f
    val animatedRadius = (size.width / 2) * 0.8f * animatedProgress

    drawCircle(
        color = strokeColor,
        radius = size.width / 40,
        center = Offset(centerX, centerY),
        style = Stroke(width = strokeWidth.toPx() / 2),
    )

    val tickIncrements = List(maxValue.toInt()) { index ->
        if (index == 0 || index == maxValue.toInt() - 1) {
            2f
        } else {
            1f
        }
    }
    val totalTicks = tickIncrements.sum()

    for (i: Int in tickIncrements.indices) {
        val tickValue = tickIncrements.take(i + 1).sum()

        if (i != maxValue.toInt() - 1) {
            drawPolygon(centerX, centerY, radius, tickValue, totalTicks, data.size, strokeColor, strokeWidth / 2)
        } else {
            drawPolygon(centerX, centerY, radius, tickValue, totalTicks, data.size, strokeColor, strokeWidth)
        }
    }

    drawPath(
        path = getPath(data, centerX, centerY, tickIncrements, animatedRadius, animatedProgress),
        color = contentColor,
    )
}

private fun DrawScope.drawPolygon(
    centerX: Float,
    centerY: Float,
    radius: Float,
    value: Float,
    maxValue: Float,
    sides: Int,
    color: Color,
    strokeWidth: Dp
) {
    val angleStep = (2 * Math.PI / sides).toFloat()

    val path = Path()
    var x = centerX + radius * value / maxValue * cos(-2 * Math.PI / 4).toFloat()
    var y = centerY + radius * value / maxValue * sin(-2 * Math.PI / 4).toFloat()
    path.moveTo(x, y)

    for (i in 1 until sides) {
        val angle = angleStep * i + (-2 * Math.PI / 4).toFloat()
        x = centerX + radius * value / maxValue * cos(angle)
        y = centerY + radius * value / maxValue * sin(angle)
        path.lineTo(x, y)
    }

    path.close()
    drawPath(path = path, color = color, style = Stroke(width = strokeWidth.toPx()))
}

private fun getPath(
    data: List<RadarData>,
    centerX: Float,
    centerY: Float,
    tickIncrements: List<Float>,
    radius: Float,
    animatedProgress: Float,
): Path {
    val path = Path()

    data.forEachIndexed { index, radarData ->
        val angle = (2 * Math.PI / data.size) * index + (-2 * Math.PI / 4).toFloat()

        val totalTick = tickIncrements.sum()

        val cumulativeSum = calculateIncrementedValue(
            value = radarData.value,
            tickIncrements = tickIncrements
        )

        val value = cumulativeSum.coerceIn(0f, totalTick)
        val animatedRadius = radius * value / totalTick * animatedProgress

        val x = centerX + animatedRadius * cos(angle).toFloat()
        val y = centerY + animatedRadius * sin(angle).toFloat()

        if (index == 0) {
            path.moveTo(x, y)
        } else {
            path.lineTo(x, y)
        }
    }
    path.close()

    return path
}

fun calculateIncrementedValue(tickIncrements: List<Float>, value: Float): Float {
    if (value <= 0) return 0f

    var cumulativeSum = 0f
    var remainingValue = value
    for (i: Int in ceil(value).toInt() - 1 downTo 0) {
        if (remainingValue >= i) cumulativeSum += (remainingValue - i) * tickIncrements[i]
        if (remainingValue > 1) remainingValue -= 1
    }

    return cumulativeSum
}

@Preview
@Composable
fun PreviewAnimatedRadarGraph() {
    Box(
        modifier = Modifier.fillMaxSize().background(color = WineyTheme.colors.background_1),
        contentAlignment = Alignment.Center
    ) {
        RadarChart(
            modifier = Modifier.fillMaxWidth(0.8f),
            data = listOf(
                RadarData("당도", 5f),
                RadarData("여운", 1f),
                RadarData("알코올", 5f),
                RadarData("탄닌", 1f),
                RadarData("바디", 5f),
                RadarData("산도", 2f)
            )
        )
    }
}