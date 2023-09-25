package com.teamwiney.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.teamwiney.ui.theme.WineyTheme

data class VerticalBarGraphData(
    val label: String,
    val score: Int,
    val color: Color
)

@Composable
fun VerticalBarGraph(
    modifier: Modifier = Modifier,
    progress: Float = 1f,
    maxValue: Int = 5,
    data: List<VerticalBarGraphData>,
    barWidth: Dp = 38.dp,
    borderColor: Color = WineyTheme.colors.gray_800,
    labelTextStyle: TextStyle = WineyTheme.typography.captionM2.copy(color = Color.White),
    scoreTextStyle: TextStyle = WineyTheme.typography.bodyB1,
    maxScoreColor: Color = WineyTheme.colors.gray_50,
    defaultScoreColor: Color = WineyTheme.colors.gray_800
) {
    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()

    val animatedProgress = remember { Animatable(0f) }

    val barWidthInPx = density.run { barWidth.toPx() }

    LaunchedEffect(true) {
        animatedProgress.animateTo(1f, tween(durationMillis = 1000))
    }

    Canvas(
        modifier = modifier
            .size(width = 280.dp, height = 236.dp)
    ) {
        val graphWidth = size.width - density.run { 12.dp.toPx() }
        val canvasHeight = size.height - density.run { 56.dp.toPx() }

        // y축 눈금 간격 계산
        val tickSpacing = canvasHeight / (maxValue + 1)

        // 그래프 경계선 그리기
        drawLine(
            color = borderColor,
            start = Offset(0f, tickSpacing),
            end = Offset(0f, canvasHeight)
        )
        drawLine(
            color = borderColor,
            start = Offset(0f, canvasHeight),
            end = Offset(size.width, canvasHeight)
        )

        // y축 눈금 그리기
        for (i in 1 until maxValue + 1) {
            val tickY = canvasHeight - i * tickSpacing
            drawLine(
                color = borderColor,
                start = Offset(0f, tickY),
                end = Offset(density.run { 12.dp.toPx() }, tickY)
            )
        }

        // 막대 그래프 그리기
        val barAreaWidth = graphWidth / data.size
        var startX = density.run { 12.dp.toPx() } + (barAreaWidth / 2) - (barWidthInPx / 2)

        val highestScore = data.maxOf { it.score }

        data.forEach { (label, value, color) ->
            val barHeight = canvasHeight * value / (maxValue + 1) * progress

            val barRect = Rect(startX, canvasHeight - barHeight, startX + barWidthInPx, canvasHeight)

            val cornerRadius = CornerRadius(
                x = density.run { 8.dp.toPx() },
                y = density.run { 8.dp.toPx() }
            )
            val path = Path().apply {
                addRoundRect(
                    RoundRect(
                        rect = Rect(
                            offset = barRect.topLeft,
                            size = Size(barWidthInPx, barHeight)
                        ),
                        topLeft = cornerRadius,
                        topRight = cornerRadius
                    )
                )
            }
            drawPath(path, color)

            // 막대 위에 값 추가
            val valueSize = textMeasurer.measure("$value").size
            val valueX = startX + (barWidthInPx - valueSize.width) / 2 - density.run { 1.5.dp.toPx() }
            val valueY = canvasHeight - valueSize.height - barHeight - density.run { 10.dp.toPx() }

            drawText(
                textMeasurer = textMeasurer,
                text = "$value",
                style = scoreTextStyle.copy(
                    color = if (value == highestScore) maxScoreColor else defaultScoreColor
                ),
                topLeft = Offset(
                    x = valueX,
                    y = valueY
                )
            )

            // 막대 밑에 레이블(텍스트) 추가
            val labelWidth = textMeasurer.measure(label).size.width
            val labelX = startX + (barWidthInPx - labelWidth) / 2 + density.run { 12.dp.toPx() }
            val labelY = canvasHeight + density.run { 20.dp.toPx() } // 조정 가능한 값

            drawText(
                textMeasurer = textMeasurer,
                text = label,
                style = labelTextStyle.copy(textAlign = TextAlign.Center),
                topLeft = Offset(x = labelX, y = labelY)
            )

            startX += barAreaWidth
        }
    }
}

@Preview
@Composable
fun PreviewWineTasteComparisonGraph() {
    Box(
        modifier = Modifier.fillMaxSize().background(WineyTheme.colors.background_1),
        contentAlignment = Alignment.Center
    ) {
        VerticalBarGraph(
            data = listOf(
                VerticalBarGraphData(
                    label = "와인의 기본맛",
                    score = 3,
                    color = WineyTheme.colors.main_2
                ),
                VerticalBarGraphData(
                    label = "취향이 비슷한 사람들이\n느낀 와인의 맛",
                    score = 5,
                    color = WineyTheme.colors.point_1
                )
            )
        )
    }
}