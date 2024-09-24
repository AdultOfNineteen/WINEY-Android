package com.teamwiney.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter.State.Empty.painter
import com.teamwiney.core.common.model.WineType
import com.teamwiney.core.common.model.WineType.Companion.convertToNoteType
import com.teamwiney.core.design.R
import com.teamwiney.ui.theme.WineyTheme

data class CardProperties(
    val wineName: String,
    val image: Int,
    val borderColor: Color,
    val gradientCircleColor: List<Color>,
    val circleColor: Color,
    val cardColor: Color
)

@Composable
fun WineCard(
    modifier: Modifier = Modifier,
    onShowDetail: () -> Unit,
    color: String,
    name: String,
    origin: String,
    varieties: String,
    price: String
) {
    val (wineName, image, borderColor, gradientCircleColor, circleColor, cardColor) = when (color) {
        WineType.RED.type -> CardProperties(
            color,
            R.mipmap.img_red_wine,
            Color(0xFFA87575),
            listOf(Color(0xFFBF3636), Color(0xFF8F034F)),
            Color(0xFF640D0D),
            Color(0xFF441010)
        )
        WineType.WHITE.type -> CardProperties(
            color,
            R.mipmap.img_white_wine,
            Color(0xFFC1BA9E),
            listOf(Color(0xFFAEAB99), Color(0xFF754A09)),
            Color(0xFF898472),
            Color(0xFF7A706D)
        )
        WineType.ROSE.type -> CardProperties(
            color,
            R.mipmap.img_rose_wine,
            Color(0xFFC9A4A1),
            listOf(Color(0xFFAA678F), Color(0xFFD29263)),
            Color(0xFFBA7A71),
            Color(0xFF8F6C64)
        )
        WineType.SPARKLING.type -> CardProperties(
            color,
            R.mipmap.img_sparkl_wine,
            Color(0xFFC1BA9E),
            listOf(Color(0xFF827D6B), Color(0xFFBAC59C)),
            Color(0xFF777151),
            Color(0xFF4F5144)
        )
        WineType.FORTIFIED.type -> CardProperties(
            color,
            R.mipmap.img_port_wine,
            Color(0xFFB09A86),
            listOf(Color(0xFF4A2401), Color(0xFF77503A)),
            Color(0xFF4F3F28),
            Color(0xFF3A2F2F)
        )
        else -> CardProperties(
            color,
            R.mipmap.img_etc_wine,
            Color(0xFF768169),
            listOf(Color(0xFF3C3D12), Color(0xFF465C18)),
            Color(0xFF2D4328),
            Color(0xFF233124)
        )
    }

    Box(
        modifier = Modifier
            .height(IntrinsicSize.Max)
            .clickable {
                onShowDetail()
            },
        contentAlignment = Alignment.TopCenter
    ) {
        CardSurface(
            modifier = modifier.fillMaxSize(),
            cardColor = cardColor,
            gradientCircleColor = gradientCircleColor,
            circleColor = circleColor
        )

        Surface(
            modifier = modifier,
            shape = RoundedCornerShape(5.dp),
            color = WineyTheme.colors.gray_50.copy(alpha = 0.1f),
            border = BorderStroke(1.dp, borderColor)
        ) {
            WineCardContent(
                modifier = modifier,
                wineColor = wineName,
                borderColor = borderColor,
                image = image,
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
    Canvas(
        modifier = Modifier
            .width(228.dp)
            .height(271.dp)
    ) {
        drawCircle(
            brush = Brush.verticalGradient(gradientCircleColor.map { it.copy(alpha = 0.7f )}),
            radius = 79.dp.toPx(),
            center = Offset(x = 79.dp.toPx(), y = 79.dp.toPx())
        )

        drawCircle(
            color = circleColor.copy(0.7f),
            radius = 89.dp.toPx(),
            center = Offset(x = 139.dp.toPx(), y = 182.dp.toPx())
        )
    }
}

@Composable
private fun CardSurface(
    modifier: Modifier = Modifier,
    cardColor: Color,
    gradientCircleColor: List<Color>,
    circleColor: Color
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .blur(60.dp)
            .padding(start = 10.dp, end = 10.dp, top = 10.dp)
            .background(
                color = cardColor.copy(alpha = 0.5f),
                shape = RoundedCornerShape(5.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        WineCardCircle(
            gradientCircleColor = gradientCircleColor,
            circleColor = circleColor
        )
    }
}

@Composable
private fun WineCardContent(
    modifier: Modifier = Modifier,
    wineColor: String,
    borderColor: Color,
    @DrawableRes image: Int,
    name: String,
    origin: String,
    varieties: String,
    price: String
) {
    Column(
        modifier = modifier
            .padding(start = 26.dp, end = 26.dp, top = 27.dp, bottom = 50.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.height(68.dp),
                text = convertToNoteType(wineColor),
                style = WineyTheme.typography.display1,
                color = WineyTheme.colors.gray_50
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = "IC_ARROW_RIGHT",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .offset(x = 5.dp)
                    .align(Alignment.CenterVertically)
            )
        }

        Text(
            text = name + "\n",
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = WineyTheme.typography.bodyM2,
            color = WineyTheme.colors.gray_50
        )
        HeightSpacer(height = 10.dp)

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp),
            thickness = 1.dp,
            color = borderColor
        )

        Row(
            modifier = Modifier.height(IntrinsicSize.Min)
        ) {
            Box(
                modifier = Modifier.fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.padding(end = 11.dp),
                    painter = painterResource(id = image),
                    contentDescription = null
                )
            }

            VerticalDivider(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight(),
                thickness = 1.dp,
                color = borderColor
            )

            Column {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = "Countries",
                        style = WineyTheme.typography.captionM3,
                        color = WineyTheme.colors.gray_50
                    )
                    HeightSpacer(height = 5.dp)
                    Text(
                        text = origin,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = WineyTheme.typography.captionB1,
                        color = WineyTheme.colors.gray_50
                    )
                }

                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp),
                    thickness = 1.dp,
                    color = borderColor
                )

                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = "Varieties",
                        style = WineyTheme.typography.captionM3,
                        color = WineyTheme.colors.gray_50
                    )
                    HeightSpacer(height = 5.dp)
                    Text(
                        text = varieties,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = WineyTheme.typography.captionB1,
                        color = WineyTheme.colors.gray_50,
                    )
                }

                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp),
                    thickness = 1.dp,
                    color = borderColor
                )

                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = "Purchase Price",
                        style = WineyTheme.typography.captionM3,
                        color = WineyTheme.colors.gray_50
                    )
                    HeightSpacer(height = 5.dp)
                    Text(
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        text = price,
                        style = WineyTheme.typography.captionB1,
                        color = WineyTheme.colors.gray_50
                    )
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
                onShowDetail = { },
                color = "SPARKLING",
                name = "캄포 마리나 프리미티도 디 만두리아",
                varieties = "모스까뗄 데 알레한드리아",
                origin = "이탈리아",
                price = "8.80"
            )
        }
    }
}