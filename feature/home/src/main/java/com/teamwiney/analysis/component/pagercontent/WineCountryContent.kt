package com.teamwiney.analysis.component.pagercontent

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.teamwiney.core.design.R
import com.teamwiney.data.network.model.response.Top3Country
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.theme.WineyTheme


@Composable
fun WineCountryContent(
    progress: Float,
    countries: List<Top3Country>
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeightSpacer(height = 33.dp)

        Row {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(5.dp)
                    .background(WineyTheme.colors.main_2)
            )
            Text(
                text = "선호 국가",
                style = WineyTheme.typography.title2,
                color = WineyTheme.colors.gray_50,
                textAlign = TextAlign.Center,
            )
        }

        Column(
            modifier = Modifier.fillMaxHeight().weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                val total = countries.sumOf { it.count }

                countries.forEachIndexed { index, country ->
                    val labelColor = when (index) {
                        0 -> WineyTheme.colors.gray_50
                        1 -> WineyTheme.colors.gray_700
                        2 -> WineyTheme.colors.gray_900
                        else -> WineyTheme.colors.gray_50
                    }

                    WineAnalysisBottle(
                        progress = progress,
                        percentage = country.count / total.toFloat(),
                        label = "${country.country}(${country.count}회)",
                        textColor = labelColor,
                    )
                }
            }
        }
    }
}

@Composable
private fun WineAnalysisBottle(
    progress: Float = 1f,
    percentage: Float = 0.7f, // 0.7f 가 100% 라고 가정
    textColor: Color = WineyTheme.colors.gray_50,
    label: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxHeight()
                .weight(0.45f)
                .aspectRatio(0.25f),
        ) {
            Image(
                painter = painterResource(id = R.mipmap.img_analysis_bottle_background),
                contentDescription = "IMG_ANALYSIS_BOTTLE_BACKGROUND",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .fillMaxHeight(percentage * progress * 0.7f)
                    .clip(RoundedCornerShape(3.dp))
                    .padding(6.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                WineyTheme.colors.main_2,
                                WineyTheme.colors.main_1,
                            )
                        )
                    )
            )
        }

        Text(
            text = label,
            style = WineyTheme.typography.bodyB2,
            color = textColor,
        )
    }
}