package com.teamwiney.auth.signup.component.bottomsheet

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
fun SendTimeExceededLimitBottomSheet(
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
            text = "인증 요청 제한 횟수를 초과했어요\n5분 뒤 처음부터 진행해주세요!",
            style = WineyTheme.typography.bodyB1,
            color = WineyTheme.colors.gray_200,
            textAlign = TextAlign.Center
        )
        HeightSpacer(height = 72.dp)
        WButton(
            text = "확인",
            onClick = {
                onConfirm()
            }
        )
        HeightSpacer(height = 10.dp)
    }
}