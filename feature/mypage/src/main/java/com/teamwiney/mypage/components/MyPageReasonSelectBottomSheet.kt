package com.teamwiney.mypage.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.theme.WineyTheme

val reasons = listOf(
    "취향에 맞지 않은 와인을 추천해줘요",
    "와인에 흥미를 잃었어요",
    "콘텐츠가 한정적이며 잘못된 정보를 제공해요",
    "더 나은 앱, 웹 또는 방법을 찾았어요",
    "서비스 장애가 많고 이용이 불편해요",
    "잘 사용하지 않아요",
    "기타"
)

@Composable
fun MyPageReasonSelectBottomSheet(
    onSelect: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = WineyTheme.colors.gray_950,
                shape = RoundedCornerShape(
                    topStart = 6.dp,
                    topEnd = 6.dp
                )
            )
            .padding(
                end = 24.dp,
                top = 10.dp,
            ),
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
        HeightSpacer(height = 10.dp)
        Column {
            reasons.forEachIndexed { index, reason ->
                Text(
                    text = reason,
                    color = WineyTheme.colors.gray_50,
                    style = WineyTheme.typography.bodyM1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelect(reason) }
                        .padding(
                            horizontal = 24.dp,
                            vertical = 20.dp
                        )
                )
                if (index != reasons.size - 1) {
                    Spacer(
                        modifier = Modifier.fillMaxWidth().background(WineyTheme.colors.gray_900)
                    )
                }
            }
        }
    }
}
