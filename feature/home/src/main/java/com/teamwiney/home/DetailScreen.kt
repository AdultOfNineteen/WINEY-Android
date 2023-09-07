package com.teamwiney.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwiney.core.design.R
import com.teamwiney.ui.components.HeightSpacerWithLine
import com.teamwiney.ui.theme.WineyTheme

@Composable
@Preview
fun DetailScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(WineyTheme.colors.background_1)
    ) {

        DetailTopBar()

        Column(modifier = Modifier.padding(20.dp)) {
            TitleAndDescription()

            HeightSpacerWithLine(
                modifier = Modifier.padding(vertical = 20.dp),
                color = WineyTheme.colors.gray_900
            )

            WineOrigin()

            HeightSpacerWithLine(
                modifier = Modifier.padding(vertical = 20.dp),
                color = WineyTheme.colors.gray_900
            )

            WineInfo()
        }
    }
}

@Composable
private fun WineInfo() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(WineyTheme.colors.main_2)
                    .size(12.dp)
            )
            Text(
                text = "취향이 비슷한 사람들이 느낀 와인의 맛",
                style = WineyTheme.typography.captionM2,
                color = WineyTheme.colors.gray_50
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 27.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(WineyTheme.colors.point_1)
                    .size(12.dp)
            )
            Text(
                text = "와인의 기본 맛",
                style = WineyTheme.typography.captionM2,
                color = WineyTheme.colors.gray_50
            )
        }
    }

    Column(verticalArrangement = Arrangement.spacedBy(50.dp)) {
        repeat(4) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "당도",
                        style = WineyTheme.typography.bodyB2,
                        color = WineyTheme.colors.gray_50,
                    )
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .height(10.dp)
                                .clip(CircleShape)
                                .background(WineyTheme.colors.main_2)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.3f)
                                .height(10.dp)
                                .clip(CircleShape)
                                .background(WineyTheme.colors.point_1)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun WineOrigin() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_dummy_wine),
            contentDescription = "WINE_IMG",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .weight(1f)
                .clip(CircleShape)
        )
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            repeat(3) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "national an thems",
                        style = WineyTheme.typography.captionM3,
                        color = WineyTheme.colors.gray_50
                    )
                    Text(
                        text = "이탈리아",
                        style = WineyTheme.typography.captionB1,
                        color = WineyTheme.colors.gray_50
                    )
                }
            }
        }
    }
}

@Composable
private fun TitleAndDescription() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.height(68.dp),
            text = "ETC",
            style = WineyTheme.typography.display1,
            color = WineyTheme.colors.gray_50
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_thismooth),
            contentDescription = null,
            tint = Color.Unspecified
        )
    }
    Text(
        text = "캄포 마리나 프리미티도 디 만두리아 캄포 마리나 프리미티도 디 만두리아 캄포 마리나 프리미티도 디 만두리아",
        style = WineyTheme.typography.bodyB2,
        color = WineyTheme.colors.gray_500
    )
}

@Composable
private fun DetailTopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp)
    ) {
        IconButton(
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.CenterStart),
            onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back_arrow_48),
                contentDescription = "IC_BACK_ARROW",
                tint = WineyTheme.colors.gray_50
            )
        }
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "와인 상세정보",
            style = WineyTheme.typography.title2,
            color = WineyTheme.colors.gray_50
        )
    }
}