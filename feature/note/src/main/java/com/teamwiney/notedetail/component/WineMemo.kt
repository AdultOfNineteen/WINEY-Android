package com.teamwiney.notedetail.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.theme.WineyTheme


@Composable
fun WineMemo() {

    val imgs = listOf<String>("1", "2", "3", "4")

    Column() {
        Text(
            text = "Feature",
            style = WineyTheme.typography.display2,
            color = WineyTheme.colors.gray_50,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        HeightSpacer(height = 20.dp)

        if (imgs.isNotEmpty()) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.width(14.dp))
                }
                items(imgs) {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(WineyTheme.colors.gray_900)
                    )
                }
                item {
                    Spacer(modifier = Modifier.width(14.dp))
                }
            }
            HeightSpacer(height = 36.dp)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .wrapContentHeight()
                .clip(RoundedCornerShape(10.dp))
                .border(
                    BorderStroke(
                        1.dp, WineyTheme.colors.main_3
                    ),
                    RoundedCornerShape(10.dp)
                )
        ) {
            Text(
                text = "저희 조는 지난 MODE1에서는 1인가구와 푸드테크 소비트렌드를 중심으로 시작하여 버즈리포트 및 매체 조사를 진행하였고, 키워드를 모아 1인 가구를 위한 맞춤형 영양분석 간편식 서비스에 대해 기획하였는데요, 이부분에서 저희는 ‘사람’에 대한 그리고 위한 서비스를 만든다는 점을 보완하기 위해 타겟을 조금 더 세분화하여 장기적으로 건강관리가 필요한 만성질환자",
                modifier = Modifier.padding(14.dp),
                style = WineyTheme.typography.captionM1,
                color = WineyTheme.colors.gray_50
            )
        }
    }
}
