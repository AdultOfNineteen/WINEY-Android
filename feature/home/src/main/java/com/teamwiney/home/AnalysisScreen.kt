package com.teamwiney.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwiney.core.common.`typealias`.SheetContent
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.drawColoredShadow
import com.teamwiney.ui.signup.SignUpTopBar
import com.teamwiney.ui.theme.WineyTheme

@Preview
@Composable
fun AnalysisScreen(
    showBottomSheet: (SheetContent) -> Unit = {},
    hideBottomSheet: () -> Unit = {},
    setOnHideBottomSheet: (() -> Unit) -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WineyTheme.colors.background_1)
            .systemBarsPadding()
            .navigationBarsPadding()
    ) {
        // TODO : TopBar가 많이 겹치는데 Button처럼 컴포넌트화 필요
        SignUpTopBar {

        }

        HeightSpacer(height = 28.dp)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = buildAnnotatedString {
                    append("나의 ")
                    withStyle(
                        style = SpanStyle(
                            color = WineyTheme.colors.main_3
                        )
                    ) {
                        append("와인 취향")
                    }
                    append(" 분석")
                },
                style = WineyTheme.typography.title1.copy(
                    color = WineyTheme.colors.gray_50
                )
            )

            HeightSpacer(height = 18.dp)

            Text(
                text = "작성한 테이스팅 노트를 기반으로 나의 와인 취향을 분석해요!",
                style = WineyTheme.typography.captionB1.copy(
                    color = WineyTheme.colors.gray_700
                )
            )
        }

        HeightSpacer(height = 32.dp)

        Box(contentAlignment = Alignment.TopCenter) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = com.teamwiney.core.design.R.mipmap.img_analysis),
                contentScale = ContentScale.FillWidth,
                contentDescription = null
            )

            StartButton(
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 92.dp)
            )

        }
    }
}

@Preview
@Composable
private fun StartButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .drawColoredShadow(
                color = Color(0xFF4B2EFF),
                cornerRadius = 46.dp
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = WineyTheme.colors.main_1
        ),
        contentPadding = PaddingValues(
            start = 73.dp,
            end = 73.dp,
            top = 16.dp,
            bottom = 16.dp
        ),
        shape = RoundedCornerShape(46.dp)
    ) {
        Text(
            text = "분석하기",
            style = WineyTheme.typography.bodyB2.copy(
                color = WineyTheme.colors.gray_50
            )
        )
    }
}