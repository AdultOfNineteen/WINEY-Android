package com.teamwiney.auth.splash

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwiney.auth.login.component.SplashBackground
import com.teamwiney.core.common.util.Constants
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.WButton
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun ForceUpdateScreen(
    versionName: String,
    updateContent: String
) {
    val context = LocalContext.current

    SplashBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .statusBarsPadding()
        ) {
            HeightSpacer(height = 70.dp)
            Text(
                "WINEY",
                style = WineyTheme.typography.display2.copy(
                    color = WineyTheme.colors.gray_400
                ),
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    "업데이트 안내",
                    style = WineyTheme.typography.title2.copy(
                        color = WineyTheme.colors.gray_50
                    ),
                )
                Spacer(modifier = Modifier.width(8.dp))
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
            }

            HeightSpacer(height = 40.dp)

            Text(
                updateContent.replace("\\n", "\n"),
                style = WineyTheme.typography.bodyB2.copy(
                    color = WineyTheme.colors.gray_50
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFF3F3F3F).copy(alpha = 0.4f),
                        shape = RoundedCornerShape(8.dp)
                    ).border(
                        BorderStroke(
                            0.2.dp, brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0x99FFFFFF),
                                    Color(0x4DFFFFFF)
                                )
                            )
                        ),
                        RoundedCornerShape(8.dp)
                    ).padding(
                        horizontal = 16.dp,
                        vertical = 20.dp
                    )
            )

            Spacer(modifier = Modifier.fillMaxHeight().weight(1f))

            Text(
                "업데이트가 원활하지 않을 경우 아래와 같이 시도해보세요" +
                        "\n · 앱 삭제 후 재설치 " +" \n · 기기 설정 - 애플리케이션 - Google Play 스토어 - 저장공간 - 데이터 삭제 및 캐시 삭제 - 업데이트 실행",
                style = WineyTheme.typography.captionM2.copy(
                    color = WineyTheme.colors.gray_700
                )
            )

            HeightSpacer(height = 30.dp)

            WButton(
                text = "업데이트",
                onClick = {
                    try {
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(Constants.MARKET_URL)
                            )
                        )
                    } catch (e: ActivityNotFoundException) {
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(Constants.PLAY_STORE_URL)
                            )
                        )
                    }
                },
                modifier = Modifier.padding(bottom = 30.dp)
            )
        }
    }
}

@Preview
@Composable
fun ForceUpdateScreenPreview() {
    ForceUpdateScreen(
        versionName = "1.0.0",
        updateContent = "더욱 쉽고 편리한\n테이스팅 노트 기록을 경험해보세요\n\n · 와인 검색 개선\n · 도수 입력 시 소수점 지원\n · 스파클링 와인 노트 작성 시 '탄산감' 항목 추가"
    )
}