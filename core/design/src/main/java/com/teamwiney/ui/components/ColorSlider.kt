package com.teamwiney.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun ColorSlider(
    onValueChange: (Color) -> Unit,
    modifier: Modifier = Modifier,
    thumbColor: Color = Color.White,
    startColor: Color = Color.Red,
    endColor: Color = Color.White,
    trackHeight: Dp,
    thumbSize: Dp
) {
    var thumbX by remember { mutableStateOf(0f) }
    val isDragging by remember { mutableStateOf(true) }

    Box {
        Canvas(
            modifier = modifier.fillMaxWidth()
                .pointerInput(true) {
                    detectHorizontalDragGestures { _, dragAmount ->
                        if (isDragging) {
                            thumbX += dragAmount
                            thumbX = thumbX.coerceIn(0f, size.width.toFloat())
                            onValueChange(calculateColorForPosition(thumbX, size.width.toFloat(), startColor, endColor))
                        }
                    }
                }
        ) {
            val width = size.width
            val height = size.height

            drawRoundRect(
                brush = Brush.horizontalGradient(
                    colors = listOf(startColor, endColor)
                ),
                topLeft = Offset(
                    x = 0f,
                    y = (height - trackHeight.toPx()) / 2
                ),
                size = Size(width = width, height = trackHeight.toPx()),
                cornerRadius = CornerRadius(100.dp.toPx())
            )

            drawCircle(
                color = thumbColor,
                radius = thumbSize.toPx() / 2,
                center = Offset(thumbX, height / 2)
            )
        }
    }
}

// TODO : 지금 현재 슬라이더의 어느 영역을 드래그 하더라도 색이 변경되는데, 원을 클릭 후 드래그해야 바뀌도록 수정 예정
private fun isInCircle(x: Float, y: Float, centerX: Float, centerY: Float, radius: Float): Boolean {
    val dx = x - centerX
    val dy = y - centerY
    return (dx * dx + dy * dy) <= (radius * radius)
}

private fun calculateColorForPosition(position: Float, width: Float, startColor: Color, endColor: Color): Color {
    val fraction = position / width
    return lerp(startColor, endColor, fraction)
}

@Preview
@Composable
fun PreviewColorSlider() {
    val startColor = Color.Red
    val endColor = Color.White
    var currentColor by remember { mutableStateOf(startColor) }

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
                onValueChange = { currentColor = it },
                startColor = startColor,
                endColor = endColor,
                trackHeight = 10.dp,
                thumbSize = 22.dp
            )
        }
    }
}