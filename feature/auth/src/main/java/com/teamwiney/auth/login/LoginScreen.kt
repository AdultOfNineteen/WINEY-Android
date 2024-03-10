package com.teamwiney.auth.login

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.common.api.ApiException
import com.teamwiney.auth.login.component.SocialLoginButton
import com.teamwiney.auth.login.component.SplashBackground
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.navigation.ReusableDestinations
import com.teamwiney.core.common.util.Constants
import com.teamwiney.core.design.R
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.dashedBorder
import com.teamwiney.ui.theme.WineyTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    appState: WineyAppState,
    viewModel: LoginViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val effectFlow = viewModel.effect

    val context = LocalContext.current

    val googleLoginResultLauncher =
        rememberLauncherForActivityResult(contract = GoogleLoginContract()) { task ->
            try {
                val gsa = task?.getResult(ApiException::class.java)
                if (gsa != null) {
                    Log.d("code", gsa.serverAuthCode.toString())
                    Log.d("idToken", gsa.idToken.toString())

                    viewModel.googleLogin(token = gsa.idToken.toString())
                }
            } catch (e: ApiException) {
                Log.d("googleLoginError", e.toString())
            }
        }

    LaunchedEffect(true) {
        effectFlow.collectLatest { effect ->
            when (effect) {
                is LoginContract.Effect.NavigateTo -> {
                    appState.navigate(effect.destination, effect.navOptions)
                }

                is LoginContract.Effect.ShowSnackBar -> {
                    appState.showSnackbar(effect.message)
                }

                is LoginContract.Effect.LaunchGoogleLogin -> {
                    googleLoginResultLauncher.launch(GOOGLE_LOGIN_REQUEST)
                }
            }
        }
    }

    SplashBackground {
        LoginTitle()
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .navigationBarsPadding()
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 53.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(36.dp)
        ) {
            if (uiState.lastLoginMethod != null) {
                Box(
                    modifier = Modifier
                        .height(30.dp)
                        .dashedBorder(1.dp, WineyTheme.colors.main_3, 32.dp)
                        .padding(horizontal = 15.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = buildAnnotatedString {
                            append("최근에 ")
                            withStyle(style = SpanStyle(WineyTheme.colors.main_3)) {
                                append(uiState.lastLoginMethod)
                            }
                            append("로 로그인 했어요")
                        },
                        color = WineyTheme.colors.point_2,
                        style = WineyTheme.typography.captionM1
                    )
                }
            } else {
                HeightSpacer(30.dp)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(21.dp, Alignment.CenterHorizontally),
            ) {
                SocialLoginButton(drawable = R.mipmap.img_kakao_login) {
                    viewModel.processEvent(LoginContract.Event.KakaoLoginButtonClicked(context))
                }
                SocialLoginButton(drawable = R.mipmap.img_google_login) {
                    viewModel.processEvent(LoginContract.Event.GoogleLoginButtonClicked)
                }
            }

            val annotatedString = buildAnnotatedString {
                append("첫 로그인 시, ")
                pushStringAnnotation(
                    tag = "clickable",
                    annotation = "서비스 이용약관"
                )
                withStyle(
                    style = SpanStyle(
                        color = WineyTheme.colors.point_2,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append("서비스 이용약관")
                }
                pop()
                append("에 동의한 것으로 간주합니다.")
            }

            ClickableText(
                text = annotatedString,
                style = WineyTheme.typography.captionM1.copy(
                    color = WineyTheme.colors.gray_700
                ),
                onClick = { offset ->
                    val annotations = annotatedString.getStringAnnotations(
                        tag = "clickable",
                        start = offset,
                        end = offset
                    )
                    if (annotations.isNotEmpty()) {
                        val clickedText = annotations[0].item
                        if (clickedText == "서비스 이용약관") {
                            appState.navigate("${ReusableDestinations.WEB_VIEW}?url=${Constants.TERMS_OF_USE_URL}&title=${"서비스 이용약관"}&subTitle=${""}")
                        }
                    }
                }
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