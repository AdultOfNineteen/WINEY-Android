package com.teamwiney.analysis.component.pagercontent

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import com.teamwiney.data.network.model.response.Top3Varietal
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun WineVarietyContent(
    progress: Float,
    varietals: List<Top3Varietal>
) {
    Column(modifier = Modifier.fillMaxSize()) {
        HeightSpacer(height = 33.dp)

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(5.dp)
                    .background(WineyTheme.colors.main_2)
            )
            Text(
                text = "선호 품종",
                style = WineyTheme.typography.title2,
                color = WineyTheme.colors.gray_50,
                textAlign = TextAlign.Center,
            )
        }
        HeightSpacer(height = 40.dp)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            VarietalContent(
                progress = progress,
                varietals = varietals
            )
        }
    }
}

@Preview
@Composable
fun VarietalContent(
    varietals: List<Top3Varietal> = listOf(
        Top3Varietal("트레비아노, 그레케토 비앙코, 프로카니코", 20),
        Top3Varietal("쉬라/시라즈", 20),
        Top3Varietal("카베르네 소비뇽 89%, 메를로 7%, 쁘띠 시라 3%, 쁘띠 베르도 1%", 20)
    ),
    progress: Float = 1f,
) {
    val textMeasurer = rememberTextMeasurer()
    val defaultTextStyle = WineyTheme.typography.bodyB1

    val textMeasureResults = remember(varietals) {
        varietals.mapIndexed { index, varietal ->
            val labelText = if (index == 0) {
                "${formatVarietalText(varietal.varietal)}\n${varietal.percent}%"
            } else {
                formatVarietalText(varietal.varietal)
            }
            val labelStyle = when (index) {
                0 -> {
                    defaultTextStyle.copy(
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
                1 -> {
                    defaultTextStyle.copy(
                        color = Color(0xFFBDBFC1),
                        textAlign = TextAlign.Center
                    )
                }
                else -> {
                    defaultTextStyle.copy(
                        color = Color(0xFF909397),
                        textAlign = TextAlign.Center
                    )
                }
            }
            textMeasurer.measure(
                text = labelText,
                style = labelStyle,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

    val gradientColors = listOf(
        listOf(
            WineyTheme.colors.main_1,
            WineyTheme.colors.main_2.copy(alpha = 0.26f)
        ),
        listOf(
            Color(0xFF8E79D0),
            Color.Transparent,
        ),
        listOf(
            Color(0xFF948FA6),
            Color.Transparent,
        )
    )

    Canvas(
        modifier = Modifier.fillMaxSize().aspectRatio(1f)
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        varietals.forEachIndexed { index, _ ->
            when (index) {
                0 -> {
                    val circleCenterX = canvasWidth * 0.32f
                    val circleCenterY = canvasHeight * 0.25f
                    val circleRadius = canvasWidth * 0.24f * progress

                    drawCircle(
                        brush = Brush.linearGradient(
                            colors = gradientColors[0],
                            start = Offset.Zero,
                            end = Offset.Infinite
                        ),
                        center = Offset(
                            x = circleCenterX,
                            y = circleCenterY
                        ),
                        radius = circleRadius
                    )

                    val textCenter = textMeasureResults[index].size.center

                    drawText(
                        textLayoutResult = textMeasureResults[index],
                        topLeft = Offset(
                            circleCenterX - textCenter.x,
                            circleCenterY - textCenter.y
                        )
                    )
                }

                1 -> {
                    val circleCenterX = canvasWidth * 0.75f
                    val circleCenterY = canvasHeight * 0.45f
                    val circleRadius = canvasWidth * 0.175f * progress

                    drawCircle(
                        brush = Brush.verticalGradient(
                            colors = gradientColors[1]
                        ),
                        center = Offset(
                            x = circleCenterX,
                            y = circleCenterY
                        ),
                        radius = circleRadius
                    )

                    val textCenter = textMeasureResults[index].size.center

                    drawText(
                        textLayoutResult = textMeasureResults[index],
                        topLeft = Offset(
                            circleCenterX - textCenter.x,
                            circleCenterY - textCenter.y
                        )
                    )
                }

                2 -> {
                    val circleCenterX = canvasWidth * 0.45f
                    val circleCenterY = canvasHeight * 0.75f
                    val circleRadius = canvasWidth * 0.15f * progress

                    drawCircle(
                        brush = Brush.verticalGradient(
                            colors = gradientColors[2]
                        ),
                        center = Offset(
                            x = circleCenterX,
                            y = circleCenterY
                        ),
                        radius = circleRadius
                    )

                    val textCenter = textMeasureResults[index].size.center

                    drawText(
                        textLayoutResult = textMeasureResults[index],
                        topLeft = Offset(
                            circleCenterX - textCenter.x,
                            circleCenterY - textCenter.y
                        )
                    )
                }
            }
        }
    }
}

private fun formatVarietalText(varietal: String): String {
    val lines = varietal.split(" ")
    val formattedLines = mutableListOf<String>()
    for (line in lines) {
        val formattedLine = if (line.length > 6) {
            line.substring(0, 5) + "..."
        } else {
            line
        }
        formattedLines.add(formattedLine)
    }
    return formattedLines.take(2).joinToString("\n")
}