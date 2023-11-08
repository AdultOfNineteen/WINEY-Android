package com.teamwiney.notewrite.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwiney.ui.theme.WineyTheme

@Composable
internal fun NoteBackgroundSurface(
    modifier: Modifier = Modifier,
    leftCircleColor: List<Color> = listOf(Color(0xFF9E977F), Color(0xFF9E977F)),
    rightCircleColor: List<Color> = listOf(Color(0x8FFF7196), Color(0x124F3F28))
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .blur(80.dp)
            .alpha(0.4f)
            .background(
                color = Color(0x993F3F3F),
                shape = RoundedCornerShape(5.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        GradientCircle(
            leftCircleColor = leftCircleColor,
            rightCircleColor = rightCircleColor
        )
    }
}

@Composable
internal fun GradientCircle(
    leftCircleColor: List<Color>,
    rightCircleColor: List<Color>
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp

    Canvas(
        modifier = Modifier
            .fillMaxSize()
    ) {
        drawCircle(
            brush = Brush.radialGradient(
                colors = leftCircleColor,
                center = Offset(x = (size.width / 3f), y = (size.height / 2.3f))
            ),
            radius = screenWidth * 1.2.toFloat(),
            center = Offset(x = (size.width / 3f), y = (size.height / 2.3f))
        )

        drawCircle(
            brush = Brush.radialGradient(
                colors = rightCircleColor,
                center = Offset(x = (size.width / 1.2f), y = (size.height / 1.8f))
            ),
            radius = screenWidth * 2.toFloat(),
            center = Offset(x = (size.width / 1.2f), y = (size.height / 1.8f))
        )
    }
}

@Preview
@Composable
fun PreviewNoteBackGroundSurface() {
    WineyTheme {
        NoteBackgroundSurface(
            modifier = Modifier.fillMaxSize(),
            leftCircleColor = listOf(Color(0xFF9E977F), Color(0xFF9E977F)),
            rightCircleColor = listOf(Color(0x8FFF7196), Color(0x124F3F28))
        )
    }
}