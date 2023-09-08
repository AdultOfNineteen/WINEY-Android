package com.teamwiney.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

@Composable
fun RoundedLinearBar(
    /*@FloatRange(from = 0.0, to = 1.0)*/
    targetProgress: Float,
    modifier: Modifier = Modifier,
    color: Color,
    height: Dp = 9.dp
) {
    val progress = remember { Animatable(0f) }

    LaunchedEffect(targetProgress) {
        progress.animateTo(targetProgress, animationSpec = tween(1000))
    }

    Column(horizontalAlignment = Alignment.End) {
        Canvas(modifier = modifier.fillMaxWidth()) {
            drawRoundedLinearBar(
                startFraction = 0f,
                endFraction = progress.value,
                height = height,
                color = color
            )
        }
    }
}

private fun DrawScope.drawRoundedLinearBar(
    startFraction: Float,
    endFraction: Float,
    height: Dp,
    color: Color,
) {
    val width = size.width
    val yOffset = 0f

    val isLtr = layoutDirection == LayoutDirection.Ltr
    val barStart = (if (isLtr) startFraction else 1f - endFraction) * width
    val barEnd = (if (isLtr) endFraction else 1f - startFraction) * width

    drawRoundRect(
        color = color,
        topLeft = Offset(barStart, yOffset),
        size = Size(barEnd, height.toPx()),
        cornerRadius = CornerRadius(4.dp.toPx())
    )
}
