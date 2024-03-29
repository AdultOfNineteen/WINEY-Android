package com.teamwiney.analysis.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.teamwiney.core.design.R
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.WButton
import com.teamwiney.ui.theme.WineyTheme


@Composable
fun AnalysisBottomContent(
    onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(WineyTheme.colors.gray_950)
            .padding(start = 24.dp, end = 24.dp, top = 10.dp, bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .width(66.dp)
                .height(5.dp)
                .background(
                    color = WineyTheme.colors.gray_900,
                    shape = RoundedCornerShape(6.dp)
                )
        )
        HeightSpacer(height = 20.dp)
        Image(
            painter = painterResource(id = R.mipmap.img_analysis_note),
            contentDescription = null,
            modifier = Modifier.size(112.dp),
            contentScale = ContentScale.Crop
        )
        HeightSpacer(height = 16.dp)
        Text(
            text = "재구매 의사가 담긴\n테이스팅 노트가 있는 경우에 볼 수 있어요!",
            style = WineyTheme.typography.bodyB1,
            color = WineyTheme.colors.gray_200,
            textAlign = TextAlign.Center
        )
        HeightSpacer(height = 72.dp)
        WButton(
            text = "확인",
            onClick = onClick,
        )
    }
}