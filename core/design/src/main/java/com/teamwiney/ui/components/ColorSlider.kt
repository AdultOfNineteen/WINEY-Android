package com.teamwiney.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.teamwiney.ui.theme.WineyTheme
import java.lang.Integer.min

@Composable
fun ColorSlider(
    initialColor: Color,
    onValueChange: (Color) -> Unit,
    modifier: Modifier = Modifier,
    thumbColor: Color = Color.White,
    barColors: List<Color> = listOf(
        Color(0xFF59002B),
        Color(0xFF6B3036),
        Color(0xFF852223),
        Color(0xFF941F25),
        Color(0xFFCB4546),
        Color(0xFFEE676B),
        Color(0xFFF18997),
        Color(0xFFE9B4A7),
        Color(0xFFF2C2B6),
        Color(0xFFEEC693),
        Color(0xFFF5E1A8),
        Color(0xFFF1FBCB),
        Color(0xFFD5DBB5)
    ),
    trackHeight: Dp,
    thumbSize: Dp
) {
    var thumbX by remember { mutableFloatStateOf(-1f) }
    var isDragging by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.height(IntrinsicSize.Min)
    ) {
        Box(
            modifier
                .clip(RoundedCornerShape(100.dp))
                .background(
                    brush = Brush.linearGradient(
                        colors = barColors
                    )
                )
                .height(trackHeight)
                .fillMaxWidth()
        )
        Canvas(
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .pointerInput(true) {
                    detectTapGestures(
                        onPress = { offset ->
                            isDragging = isInCircle(
                                offset.x,
                                offset.y,
                                thumbX,
                                size.height.toFloat() / 2,
                                thumbSize.toPx() / 2
                            )
                        }
                    )
                }
                .pointerInput(true) {
                    detectHorizontalDragGestures { _, dragAmount ->
                        if (isDragging) {
                            thumbX += dragAmount
                            thumbX = thumbX.coerceIn(0f, size.width.toFloat())
                            onValueChange(
                                calculateColorForPosition(
                                    thumbX,
                                    size.width.toFloat(),
                                    barColors
                                )
                            )
                        }
                    }
                }
        ) {
            val height = size.height

            if (thumbX == -1f) {
                // 최초 1회 initialColor에 해당하는 thumbX 계산
                thumbX = calculatePositionForColor(initialColor, size.width, barColors)
            }

            drawCircle(
                color = thumbColor,
                radius = thumbSize.toPx() / 2,
                center = Offset(thumbX, height / 2)
            )
        }
    }
}

private fun isInCircle(x: Float, y: Float, centerX: Float, centerY: Float, radius: Float): Boolean {
    val dx = x - centerX
    val dy = y - centerY

    return (dx * dx + dy * dy) <= (radius * radius)
}

private fun calculateColorForPosition(
    position: Float,
    width: Float,
    barColors: List<Color>
): Color {
    val fraction = position / width
    val colorPosition = (fraction * (barColors.size - 1)).toInt()
    val startColor = barColors[colorPosition]
    val endColor = barColors[min(colorPosition + 1, barColors.size - 1)]
    return lerp(startColor, endColor, fraction * (barColors.size - 1) - colorPosition)
}

private fun calculatePositionForColor(
    color: Color,
    width: Float,
    barColors: List<Color>
): Float {
    var minDistance = Float.MAX_VALUE
    var closestFraction = -1f

    for (i in 0 until barColors.size - 1) {
        val startColor = barColors[i]
        val endColor = barColors[i + 1]
        val interpolatedColor = lerp(startColor, endColor, 0.5f) // 중간 색상을 보간하여 사용
        val distance = calculateColorDistance(color, interpolatedColor)

        if (distance < minDistance) {
            minDistance = distance
            val fraction = i.toFloat() / (barColors.size - 1)
            closestFraction = adjustFraction(fraction, i, barColors)
        }
    }

    return closestFraction * width
}

/***
 * 두 색상 사이의 거리를 고려하여 보정된 fraction을 반환합니다.
 */
private fun adjustFraction(
    fraction: Float,
    colorIndex: Int,
    barColors: List<Color>
): Float {
    val previousColor = if (colorIndex > 0) barColors[colorIndex - 1] else barColors[colorIndex]
    val nextColor = if (colorIndex < barColors.size - 1) barColors[colorIndex + 1] else barColors[colorIndex]

    val distance = calculateColorDistance(previousColor, nextColor)
    val adjustedFraction = fraction + (1f / (barColors.size - 1)) * distance
    return adjustedFraction.coerceIn(0f, 1f)
}


/**
 * 두 색상 사이의 유클리드 거리를 계산합니다.
 */
private fun calculateColorDistance(color1: Color, color2: Color): Float {
    val deltaL = color2.red - color1.red
    val deltaA = color2.green - color1.green
    val deltaB = color2.blue - color1.blue
    return kotlin.math.sqrt(deltaL * deltaL + deltaA * deltaA + deltaB * deltaB)
}

@Preview
@Composable
fun PreviewColorSlider() {
    val barColors = listOf(
        Color(0xFF59002B),
        Color(0xFF6B3036),
        Color(0xFF852223),
        Color(0xFF941F25),
        Color(0xFFCB4546),
        Color(0xFFEE676B),
        Color(0xFFF18997),
        Color(0xFFE9B4A7),
        Color(0xFFF2C2B6),
        Color(0xFFEEC693),
        Color(0xFFF5E1A8),
        Color(0xFFF1FBCB),
        Color(0xFFD5DBB5)
    )
    var currentColor by remember { mutableStateOf(barColors[0]) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WineyTheme.colors.background_1),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Spacer(
                modifier = Modifier
                    .background(
                        brush = Brush.radialGradient(listOf(currentColor, Color.Transparent)),
                        shape = CircleShape
                    )
                    .size(48.dp)
            )

            ColorSlider(
                initialColor = barColors[6],
                onValueChange = { currentColor = it },
                barColors = barColors,
                trackHeight = 10.dp,
                thumbSize = 22.dp
            )
        }
    }
}