package com.teamwiney.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
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

enum class CardConfig(
    val wineColor: String,
    @DrawableRes val image: Int,
    val borderColor: Color,
    val gradientCircleColor: List<Color>,
    val circleColor: Color,
    val cardColor: Color
) {
    Red(
        wineColor = "RED",
        image = R.drawable.ic_red_wine,
        borderColor = Color(0xFFA87575),
        gradientCircleColor = listOf(Color(0xFFBF3636), Color(0xFF8F034F)),
        circleColor = Color(0xFF640D0D),
        cardColor = Color(0xFF441010)
    ),
    White(
        wineColor = "WHITE",
        image = R.drawable.ic_white_wine,
        borderColor = Color(0xFF3A3D40),
        gradientCircleColor = listOf(Color(0xFFAEAB99), Color(0xFF754A09)),
        circleColor = Color(0xFF898472),
        cardColor = Color(0xFF7A706D)
    );
}

@Composable
fun WineCard(
    modifier: Modifier = Modifier,
    cardConfig: CardConfig,
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
        CardSurface(
            circleRadius = 38.dp,
            circleOffsetY = 10.dp,
            cardColor = cardConfig.cardColor,
            borderColor = cardConfig.borderColor,
            gradientCircleColor = cardConfig.gradientCircleColor,
            circleColor = cardConfig.circleColor
        )
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
            border = BorderStroke(1.dp, cardConfig.borderColor)
        ) {
            WineCardContent(
                wineColor = cardConfig.wineColor,
                borderColor = cardConfig.borderColor,
                image = cardConfig.image,
                name = name,
                origin = origin,
                varieties = varieties,
                price = price
            )
        }
    }
}

@Composable
private fun WineCardCircle(
    gradientCircleColor: List<Color>,
    circleColor: Color
) {
    Canvas(modifier = Modifier
        .width(228.dp)
        .height(271.dp)) {
        drawCircle(
            brush = Brush.verticalGradient(gradientCircleColor),
            radius = 79.dp.toPx(),
            center = Offset(x = 79.dp.toPx(), y = 79.dp.toPx())
        )

        drawCircle(
            color = circleColor,
            radius = 89.dp.toPx(),
            center = Offset(x = 139.dp.toPx(), y = 182.dp.toPx())
        )
    }
}

@Composable
private fun CardSurface(
    modifier: Modifier = Modifier,
    cardColor: Color,
    borderColor: Color,
    gradientCircleColor: List<Color>,
    circleColor: Color,
    circleRadius: Dp,
    circleOffsetY: Dp
) {
    Box {
        Column {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .height(440.dp)
                    .blur(30.dp)
                    .offset(y = circleOffsetY)
                    .padding(horizontal = 10.dp)
                    .background(color = cardColor),
                contentAlignment = Alignment.Center
            ) {
                WineCardCircle(
                    gradientCircleColor = gradientCircleColor,
                    circleColor = circleColor
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = -circleRadius)
                    .padding(horizontal = 18.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxWidth()) {
                    val canvasWidth = size.width

                    val arcWidth = (circleRadius * 2).toPx()
                    val arcStartX = (canvasWidth - arcWidth) / 2
                    val arcStartY = circleOffsetY.toPx()

                    drawArc(
                        color = cardColor,
                        startAngle = 180f,
                        sweepAngle = 180f,
                        useCenter = true,
                        topLeft = Offset(arcStartX, arcStartY),
                        size = Size(arcWidth, arcWidth)
                    )

                    drawPath(
                        Path().apply {
                            addRoundRect(
                                RoundRect(
                                    rect = Rect(
                                        offset = Offset(0f, circleRadius.toPx()),
                                        size = Size(canvasWidth, circleOffsetY.toPx()),
                                    ),
                                    bottomLeft = CornerRadius(10.dp.toPx(), 10.dp.toPx()),
                                    bottomRight = CornerRadius(10.dp.toPx(), 10.dp.toPx())
                                )
                            )
                        },
                        color = cardColor
                    )
                }
            }
        }
    }
}

@Composable
private fun WineCardContent(
    wineColor: String,
    borderColor: Color,
    @DrawableRes image: Int,
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
                text = wineColor,
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

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth().height(1.dp),
            thickness = 1.dp,
            color = borderColor
        )

        Row {
            Box(
                modifier = Modifier.fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.padding(start = 8.dp, end = 18.dp),
                    painter = painterResource(id = image),
                    contentDescription = null
                )
            }

            VerticalDivider(
                modifier = Modifier.width(1.dp).fillMaxHeight(),
                thickness = 1.dp,
                color = borderColor
            )

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

                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth().height(1.dp),
                    thickness = 1.dp,
                    color = borderColor
                )

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

                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth().height(1.dp),
                    thickness = 1.dp,
                    color = borderColor
                )

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
                cardConfig = CardConfig.White,
                name = "캄포 마리나 프리미티도 디 만두리아",
                varieties = "모스까뗄 데 알레한드리아",
                origin = "이탈리아",
                price = "8.80"
            )
        }
    }
}