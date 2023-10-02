package com.teamwiney.notecollection.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamwiney.core.design.R
import com.teamwiney.ui.components.CardProperties
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.theme.Chaviera
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun NoteWineCard(wine: CardProperties) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(7.dp))
                .border(
                    BorderStroke(
                        1.dp, brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF9671FF),
                                Color(0x109671FF),
                            )
                        )
                    ),
                    RoundedCornerShape(7.dp)
                )
                .background(WineyTheme.colors.background_1)
        ) {
            NoteCardSurface(
                modifier = Modifier.fillMaxSize(),
                cardColor = wine.cardColor,
                gradientCircleColor = wine.gradientCircleColor,
                circleColor = wine.circleColor
            )
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.padding(start = 20.dp, top = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = wine.wineName,
                        style = TextStyle(
                            fontFamily = Chaviera,
                            fontWeight = FontWeight.Normal,
                            fontSize = 25.sp,
                        ),
                        color = WineyTheme.colors.gray_50,
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.ic_thismooth),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .padding(bottom = 5.dp)
                            .size(13.dp)
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_sparkl_wine),
                    contentDescription = "IMG_SPARKL_WINE",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 10.dp)
                        .offset(y = (-10).dp)
                )
            }

        }
        HeightSpacer(height = 10.dp)
        Text(
            text = "캄포 마리니 어쩌구저쩌구ㅁㄴㅇㅁㄴㅇㅁㄴㅇ",
            color = WineyTheme.colors.gray_50,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth(),
            style = WineyTheme.typography.captionB1,
        )
        HeightSpacer(height = 2.dp)
        Text(
            text = "이탈리아 / 5점",
            color = WineyTheme.colors.gray_700,
            style = WineyTheme.typography.captionM2
        )
    }
}

@Composable
private fun NoteCardSurface(
    modifier: Modifier = Modifier,
    cardColor: Color,
    gradientCircleColor: List<Color>,
    circleColor: Color
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .blur(30.dp)
            .alpha(0.6f)
            .background(
                color = cardColor,
                shape = RoundedCornerShape(5.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        NoteWineCardCircle(
            gradientCircleColor = gradientCircleColor,
            circleColor = circleColor
        )
    }
}

@Composable
private fun NoteWineCardCircle(
    gradientCircleColor: List<Color>,
    circleColor: Color
) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
    ) {
        drawCircle(
            brush = Brush.verticalGradient(gradientCircleColor),
            radius = 35.dp.toPx(),
            center = Offset(x = 40.dp.toPx(), y = 20.dp.toPx())
        )

        drawCircle(
            color = circleColor,
            radius = 75.dp.toPx(),
            center = Offset(x = (size.width / 1.3f), y = (size.height / 1.3f))
        )
    }
}