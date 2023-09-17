package com.teamwiney.auth.signup.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.teamwiney.core.design.R
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.WButton
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun SendMessageBottomSheet(
    modifier: Modifier = Modifier,
    containerColor: Color = WineyTheme.colors.gray_950,
    onConfirm: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = containerColor,
                shape = RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp)
            )
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
            painter = painterResource(id = R.mipmap.img_lock),
            contentDescription = null
        )
        HeightSpacer(height = 16.dp)
        Text(
            text = "인증번호가 발송되었어요\n3분 안에 인증번호를 입력해주세요",
            style = WineyTheme.typography.bodyB1,
            color = WineyTheme.colors.gray_200,
            textAlign = TextAlign.Center
        )
        HeightSpacer(height = 14.dp)
        Text(
            text = "*인증번호 요청 3회 초과 시 5분 제한",
            style = WineyTheme.typography.captionM2,
            color = WineyTheme.colors.gray_600
        )
        HeightSpacer(height = 40.dp)
        WButton(
            text = "확인",
            onClick = {
                onConfirm()
            }
        )
        HeightSpacer(height = 20.dp)
    }
}