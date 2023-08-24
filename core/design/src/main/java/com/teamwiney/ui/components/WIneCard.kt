package com.teamwiney.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamwiney.core.design.R
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun WineCard(
    modifier: Modifier = Modifier,
    color: String,
    name: String,
    origin: String,
    price: String
) {
    Box(
        modifier = modifier
            .height(IntrinsicSize.Max)
            // LazyRow 사용 시 주석 처리
            .fillMaxWidth()
            .clip(
                TicketShape(
                    circleRadius = 38.dp,
                    circleOffsetY = 20.dp,
                    cornerSize = CornerSize(4.dp)
                )
            )
            .background(
                brush = Brush.verticalGradient(
                    listOf(Color(0xFF212327), WineyTheme.colors.gray_900)
                ),
            )
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    listOf(Color.White.copy(alpha = 0.3f), WineyTheme.colors.background_1)
                ),
                shape = TicketShape(
                    circleRadius = 38.dp,
                    circleOffsetY = 20.dp,
                    cornerSize = CornerSize(4.dp)
                )
            )
    ) {
        // 여기는 그냥 배경 원 그라데이션 코드상으로 넣은거예요
        // 이미지 받으면은 이미지 Box 내에 넣고 Box 블러 처리하면 될 거 같아요
        Box(
            modifier = Modifier
                .fillMaxSize()
                .blur(50.dp)
        ) {
            BlurredCircle(
                modifier = Modifier
                    .padding(start = 23.dp, top = 63.dp)
                    .size(157.dp),
                colors = listOf(Color(0xFF6F0303), Color(0xFF6F036400))
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .blur(50.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            BlurredCircle(
                modifier = Modifier
                    .padding(end = 20.dp, bottom = 48.dp)
                    .size(178.dp),
                colors = listOf(Color(0xFF6F0303), Color(0xFF6F036400))
            )
        }

        WineCardContent(
            color = color,
            name = name,
            origin = origin,
            price = price
        )
    }
}

@Composable
private fun WineCardContent(
    color: String,
    name: String,
    origin: String,
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
            .padding(30.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = color,
                style = TextStyle(
                    fontSize = 54.sp,
                    fontFamily = FontFamily(Font(R.font.chaviera)),
                    fontWeight = FontWeight(400),
                    color = WineyTheme.colors.gray_50,
                    textAlign = TextAlign.Center,
                )
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_thismooth),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
        Text(
            text = name,
            style = WineyTheme.typography.bodyM2,
            color = WineyTheme.colors.gray_50
        )
        HeightSpacer(height = 10.dp)

        HorizontalGradientDivider(colorStops = dividerColorSteps)

        Row {
            Box(
                modifier = Modifier.fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null
                )
            }

            VerticalGradientDivider(colorStops = dividerColorSteps)

            Column {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = "national anthems",
                        style = TextStyle(
                            fontSize = 8.sp,
                            fontFamily = FontFamily(Font(R.font.chaviera)),
                            fontWeight = FontWeight(400),
                            color = WineyTheme.colors.gray_50,
                        )
                    )
                    HeightSpacer(height = 5.dp)
                    Text(
                        modifier = Modifier,
                        text = origin,
                        style = WineyTheme.typography.bodyB1,
                        color = WineyTheme.colors.gray_50
                    )
                }

                HorizontalGradientDivider(colorStops = dividerColorSteps)

                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = "Varieties",
                        style = TextStyle(
                            fontSize = 8.sp,
                            fontFamily = FontFamily(Font(R.font.chaviera)),
                            fontWeight = FontWeight(400),
                            color = WineyTheme.colors.gray_50,
                        )
                    )
                    HeightSpacer(height = 5.dp)
                    Text(
                        modifier = Modifier,
                        text = "프리미티보",
                        style = WineyTheme.typography.bodyB1,
                        color = WineyTheme.colors.gray_50
                    )
                }

                HorizontalGradientDivider(colorStops = dividerColorSteps)

                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = "Purchase Price",
                        style = TextStyle(
                            fontSize = 8.sp,
                            fontFamily = FontFamily(Font(R.font.chaviera)),
                            fontWeight = FontWeight(400),
                            color = WineyTheme.colors.gray_50,
                        )
                    )
                    HeightSpacer(height = 5.dp)
                    Text(
                        modifier = Modifier,
                        text = price,
                        style = WineyTheme.typography.bodyB1,
                        color = WineyTheme.colors.gray_50
                    )
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
        val circleRadiusInPx = with(density) { circleRadius.toPx() }
        val circleOffsetYInPx = with(density) { circleOffsetY.toPx() }
        return Path().apply {
            reset()

            lineTo(x = 0F, y = 0F)
            lineTo(middleX, y = 0F)
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
                origin = "이탈리아",
                price = "8.80"
            )
        }
    }
}