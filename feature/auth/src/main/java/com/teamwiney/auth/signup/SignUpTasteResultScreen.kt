package com.teamwiney.auth.signup

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.navigation.AuthDestinations
import com.teamwiney.core.common.navigation.HomeDestinations
import com.teamwiney.core.design.R
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.WButton
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun SignUpTasteResultScreen(
    appState: WineyAppState,
    taste: String
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        HeightSpacer(height = 68.dp)


        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            Text(
                text = "이제 와인을\n마시러 가보실까요?",
                style = WineyTheme.typography.title1,
                color = WineyTheme.colors.gray_50
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                WineTasteCard(taste = taste)
            }

            WButton(
                modifier = Modifier.padding(bottom = 30.dp),
                text = "와이니 시작하기",
                onClick = {
                    appState.navigate(HomeDestinations.ROUTE) {
                        popUpTo(AuthDestinations.SignUp.ROUTE) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}

@Composable
private fun WineTasteCard(
    taste: String
) {
    Box(
        modifier = Modifier
            .width(IntrinsicSize.Max)
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
            .clip(RoundedCornerShape(7.dp))
            .background(
                color = Color(0x993F3F3F),
                shape = RoundedCornerShape(5.dp)
            ),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier.padding(
                top = 44.dp,
                bottom = 17.dp,
                start = 20.dp,
                end = 20.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(
                    id = if (taste == "red") {
                        R.mipmap.img_taste_red_wine
                    } else {
                        R.mipmap.img_taste_white_wine
                    }
                ),
                contentDescription = null
            )

            HeightSpacer(height = 12.dp)

            Text(
                modifier = Modifier
                    .background(
                        color = WineyTheme.colors.main_1,
                        shape = RoundedCornerShape(48.dp)
                    )
                    .padding(
                        horizontal = 10.dp,
                        vertical = 3.dp
                    ),
                text = if (taste == "red") "레드 와인" else "화이트 와인",
                style = WineyTheme.typography.captionM1.copy(color = WineyTheme.colors.gray_50)
            )

            HeightSpacer(height = 17.dp)

            Text(
                text = if (taste == "red") "산미의 중요성을 아는 와이너" else "향을 즐길 줄 아는 와이너",
                style = WineyTheme.typography.bodyM1.copy(color = WineyTheme.colors.gray_50)
            )

            HeightSpacer(height = 9.dp)

            Text(
                text = "담당자에게 해당 화면을 보여주세요!",
                style = WineyTheme.typography.captionM1.copy(color = WineyTheme.colors.gray_700)
            )
        }
    }
}