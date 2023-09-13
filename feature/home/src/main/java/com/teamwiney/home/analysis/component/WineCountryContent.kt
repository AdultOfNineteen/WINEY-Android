package com.teamwiney.home.analysis.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
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
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.theme.WineyTheme


@Composable
fun WineCountryContent() {
    Column(modifier = Modifier.fillMaxSize()) {
        HeightSpacer(height = 66.dp)

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
                text = "선호 국가",
                style = WineyTheme.typography.title2,
                color = WineyTheme.colors.gray_50,
                textAlign = TextAlign.Center,
            )
        }
        HeightSpacer(height = 40.dp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(horizontal = 54.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            WineAnalysisBottle(
                0.6f,
                textColor = WineyTheme.colors.gray_50
            )
            Spacer(modifier = Modifier.weight(0.2f))
            WineAnalysisBottle(
                0.4f,
                textColor = WineyTheme.colors.gray_700
            )
            Spacer(modifier = Modifier.weight(0.2f))
            WineAnalysisBottle(
                0.2f,
                textColor = WineyTheme.colors.gray_900
            )
        }
    }
}

@Composable
private fun RowScope.WineAnalysisBottle(
    percentage: Float = 0.6f, // 0.6f 가 100% 라고 가정
    textColor: Color = WineyTheme.colors.gray_50,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .weight(0.5f)
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth()
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
                    .fillMaxHeight(percentage)
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
            text = "이탈리야(3회)",
            style = WineyTheme.typography.bodyB2,
            color = textColor,
        )
    }
}