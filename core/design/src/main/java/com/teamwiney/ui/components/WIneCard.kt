package com.teamwiney.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.teamwiney.core.design.R
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun WineCard(
    modifier: Modifier = Modifier,
    color: String,
    name: String,
    origin: String,
    varieties: String,
    price: String
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Box {
            // TODO : 블러 처리 안된 영역 (원) 처리
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(440.dp)
                        .blur(70.dp)
                        .offset(y = 10.dp)
                        .padding(horizontal = 10.dp)
                        .background(color = Color(0xFF310909))
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .padding(horizontal = 18.dp)
                        .background(color = Color(0xFF310909))
                )
            }
        }
        Surface(
            modifier = modifier
                .height(IntrinsicSize.Max)
                .fillMaxWidth(),
            shape = TicketShape(
                circleRadius = 38.dp,
                circleOffsetY = 10.dp,
                cornerSize = CornerSize(5.dp)
            ),
            color = WineyTheme.colors.gray_50.copy(alpha = 0.1f),
            border = BorderStroke(1.dp, WineyTheme.colors.gray_900)
        ) {
            WineCardContent(
                color = color,
                name = name,
                origin = origin,
                varieties = varieties,
                price = price
            )
        }
    }
}

@Composable
private fun WineCardContent(
    color: String,
    name: String,
    origin: String,
    varieties: String,
    price: String
) {
    val dividerColorSteps = arrayOf(
        0.0f to WineyTheme.colors.gray_50,
        0.7f to WineyTheme.colors.gray_400,
        1.0f to WineyTheme.colors.gray_700
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .width(IntrinsicSize.Max)
            .height(440.dp)
            .padding(start = 30.dp, end = 30.dp, top = 30.dp, bottom = 58.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = color,
                style = WineyTheme.typography.display1,
                color = WineyTheme.colors.gray_50
            )
            Icon(
                modifier = Modifier.offset(y = (-5).dp),
                painter = painterResource(id = R.drawable.ic_thismooth),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
        Text(
            modifier = Modifier.offset(y = (-10).dp),
            text = name,
            style = WineyTheme.typography.bodyM2,
            color = WineyTheme.colors.gray_50
        )
        HeightSpacer(height = 25.dp)

        HorizontalGradientDivider(colorStops = dividerColorSteps)

        Row {
            Box(
                modifier = Modifier.fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.padding(start = 8.dp, end = 18.dp),
                    painter = painterResource(id = R.drawable.ic_red_wine),
                    contentDescription = null
                )
            }

            VerticalGradientDivider(colorStops = dividerColorSteps)

            Column {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "national anthems",
                        style = WineyTheme.typography.captionM3,
                        color = WineyTheme.colors.gray_50
                    )
                    HeightSpacer(height = 5.dp)
                    Text(
                        modifier = Modifier,
                        text = origin,
                        style = WineyTheme.typography.bodyB1,
                        color = WineyTheme.colors.gray_50
                    )
                    HeightSpacer(height = 5.dp)
                }

                HorizontalGradientDivider(colorStops = dividerColorSteps)

                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "Varieties",
                        style = WineyTheme.typography.captionM3,
                        color = WineyTheme.colors.gray_50
                    )
                    HeightSpacer(height = 5.dp)
                    Text(
                        modifier = Modifier,
                        text = varieties,
                        style = WineyTheme.typography.bodyB1,
                        color = WineyTheme.colors.gray_50
                    )
                    HeightSpacer(height = 5.dp)
                }

                HorizontalGradientDivider(colorStops = dividerColorSteps)

                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "Purchase Price",
                        style = WineyTheme.typography.captionM3,
                        color = WineyTheme.colors.gray_50
                    )
                    HeightSpacer(height = 5.dp)
                    Text(
                        modifier = Modifier,
                        text = price,
                        style = WineyTheme.typography.bodyB1,
                        color = WineyTheme.colors.gray_50
                    )
                    HeightSpacer(height = 5.dp)
                }
            }
        }
    }
}

@Composable
private fun BlurredCircle(
    modifier: Modifier = Modifier,
    colors: List<Color>
) {
    Spacer(
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(colors),
                shape = CircleShape
            )
    )
}

@Composable
private fun HorizontalGradientDivider(
    colorStops: Array<Pair<Float, Color>>
) {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Brush.horizontalGradient(colorStops = colorStops))
    )
}

@Composable
private fun VerticalGradientDivider(
    colorStops: Array<Pair<Float, Color>>
) {
    Spacer(
        modifier = Modifier
            .width(1.dp)
            .fillMaxHeight()
            .background(Brush.verticalGradient(colorStops = colorStops))
    )
}

class TicketShape(
    private val circleRadius: Dp,
    private val circleOffsetY: Dp,
    private val cornerSize: CornerSize
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(path = getPath(size, density))
    }

    private fun getPath(size: Size, density: Density): Path {
        val roundedRect = RoundRect(size.toRect(), CornerRadius(cornerSize.toPx(size, density)))
        val roundedRectPath = Path().apply { addRoundRect(roundedRect) }
        return Path.combine(
            operation = PathOperation.Intersect,
            path1 = roundedRectPath,
            path2 = getTicketPath(size, density)
        )
    }

    private fun getTicketPath(size: Size, density: Density): Path {
        val middleX = size.width.div(other = 2)
        val circleRadiusInPx = density.run { circleRadius.toPx() }
        val circleOffsetYInPx = density.run { circleOffsetY.toPx() }
        return Path().apply {
            reset()

            lineTo(x = 0F, y = 0F)
            lineTo(x = size.width, y = 0F)
            lineTo(x = size.width, y = size.height)
            lineTo(middleX, y = size.height)

            arcTo(
                rect = Rect(
                    left = middleX.minus(circleRadiusInPx),
                    top = size.height - circleRadiusInPx + circleOffsetYInPx,
                    right = middleX.plus(circleRadiusInPx),
                    bottom = size.height + circleRadiusInPx - circleOffsetYInPx
                ),
                startAngleDegrees = 0F,
                sweepAngleDegrees = -180F,
                forceMoveTo = false
            )

            lineTo(x = 0F, y = size.height)
            lineTo(x = 0F, y = 0F)
        }
    }
}

@Preview
@Composable
fun PreviewWineCard() {
    WineyTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            WineCard(
                color = "RED",
                name = "캄포 마리나 프리미티도 디 만두리아",
                varieties = "모스까뗄 데 알레한드리아",
                origin = "이탈리아",
                price = "8.80"
            )
        }
    }
}