package com.teamwiney.auth.splash.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun SoftUpdateBottomSheet(
    modifier: Modifier = Modifier,
    containerColor: Color = WineyTheme.colors.gray_950,
    versionName: String,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = containerColor,
                shape = RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp)
            )
            .padding(start = 24.dp, end = 24.dp, top = 30.dp, bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            versionName,
            style = WineyTheme.typography.captionM2.copy(
                color = WineyTheme.colors.gray_50
            ),
            modifier = Modifier
                .background(
                    color = WineyTheme.colors.main_1,
                    shape = RoundedCornerShape(48.dp)
                )
                .padding(
                    horizontal = 10.dp,
                    vertical = 4.dp
                )
        )

        HeightSpacer(height = 8.dp)

        Text(
            "업데이트 안내",
            style = WineyTheme.typography.headline.copy(
                color = WineyTheme.colors.gray_50
            )
        )
        
        HeightSpacer(height = 25.dp)

        Text(
            "WINEY를 최신 버전으로 업데이트 후\n더욱 쉽고 즐거워진 와인 기록을 경험해 보세요!",
            style = WineyTheme.typography.bodyM2.copy(
                color = WineyTheme.colors.gray_50
            ),
            textAlign = TextAlign.Center
        )
        
        HeightSpacer(height = 25.dp)

        Text(
            "업데이트가 원활하지 않을 경우 아래와 같이 시도해보세요" +
                    "\n · 앱 삭제 후 재설치 " +" \n · 기기 설정 - 애플리케이션 - Google Play 스토어 - 저장공간 - 데이터 삭제 및 캐시 삭제 - 업데이트 실행",
            style = WineyTheme.typography.captionM2.copy(
                color = WineyTheme.colors.gray_700
            )
        )

        HeightSpacer(height = 20.dp)
        BottomSheetSelectionButton(
            onConfirm = { onConfirm() },
            onCancel = { onCancel() }
        )
        HeightSpacer(height = 10.dp)
    }
}

@Composable
private fun BottomSheetSelectionButton(
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp),
        color = WineyTheme.colors.gray_700
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(67.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            color = Color.Transparent,
            onClick = { onCancel() }
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "나중에",
                    style = WineyTheme.typography.bodyB1,
                    color = WineyTheme.colors.gray_600
                )
            }
        }
        VerticalDivider(
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
                .padding(vertical = 21.dp),
            color = WineyTheme.colors.gray_700
        )
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            color = Color.Transparent,
            onClick = { onConfirm() }
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "업데이트",
                    style = WineyTheme.typography.bodyB1,
                    color = WineyTheme.colors.gray_100
                )
            }
        }
    }
}