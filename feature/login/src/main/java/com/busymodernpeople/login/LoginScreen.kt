package com.busymodernpeople.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.teamwiney.core.design.R
import com.teamwiney.ui.components.SocialLoginButton
import com.teamwiney.ui.components.dashedBorder
import com.teamwiney.ui.splash.SplashBackground
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun LoginScreen(
    onKaKaoLogin: () -> Unit,
    onGoogleLogin: () -> Unit
) {

    SplashBackground {
        LoginTitle()
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 53.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(36.dp)
        ) {

            Box(
                modifier = Modifier
                    // Dashed Border
                    .dashedBorder(1.dp, WineyTheme.colors.main_3, 32.dp)
                    .padding(7.dp, 4.dp)
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("최근에 ")
                        withStyle(style = SpanStyle(WineyTheme.colors.main_3)) {
                            append("카카오톡")
                        }
                        append("으로 로그인 했습니다.")
                    },
                    color = WineyTheme.colors.point_2,
                    style = WineyTheme.typography.captionM1
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(21.dp, Alignment.CenterHorizontally),
            ) {
                SocialLoginButton(drawable = R.mipmap.img_kakao_login) {
                    // TODO 카카오 로그인 진행
                    onKaKaoLogin()
                }
                SocialLoginButton(drawable = R.mipmap.img_google_login) {
                    // TODO 구글 로그인 진행
                    onGoogleLogin()
                }
            }
            Text(
                text = buildAnnotatedString {
                    append("첫 로그인 시, ")
                    withStyle(
                        style = SpanStyle(
                            color = WineyTheme.colors.point_2,
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append("서비스 이용약관")
                    }
                    append("에 동의한 것으로 간주합니다.")
                },
                color = WineyTheme.colors.gray_700,
                style = WineyTheme.typography.captionM1
            )
        }
    }
}

@Composable
private fun BoxScope.LoginTitle() {
    Box(
        modifier = Modifier.Companion
            .align(Alignment.TopCenter)
            .fillMaxWidth()
            .fillMaxHeight(0.6f)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            Text(
                text = "와인 취향을 찾는 나만의 여정",
                color = Color(0xFFDEDEDE),
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(0.dp))
            Image(
                painter = painterResource(id = R.mipmap.img_winey_logo),
                contentDescription = null,
                modifier = Modifier.size(74.dp, 68.dp)
            )
            Image(
                painter = painterResource(id = R.mipmap.img_winey_logo_title),
                contentDescription = null,
                modifier = Modifier.size(128.dp, 36.dp)
            )
        }
    }
}