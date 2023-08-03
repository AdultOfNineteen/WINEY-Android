package com.busymodernpeople.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.teamwiney.core.design.R
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.PhoneNumberVisualTransformation
import com.teamwiney.ui.components.SignUpTopBar
import com.teamwiney.ui.components.WButton
import com.teamwiney.ui.components.WTextField
import com.teamwiney.ui.theme.WineyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpPhoneScreen(
    onBack: () -> Unit,
    onConfirm: () -> Unit
) {

    var phoneNumber by remember {
        mutableStateOf("")
    }
    var errorState by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(phoneNumber) {
        errorState = !(phoneNumber.length == 11 || phoneNumber.isEmpty())
    }

    var showSheet by remember { mutableStateOf(false) }
    if (showSheet) {
        val modalBottomSheetState = rememberModalBottomSheetState()

        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = modalBottomSheetState,
            dragHandle = { BottomSheetDefaults.DragHandle() },
            containerColor = WineyTheme.colors.gray_950
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth().padding(start = 24.dp, end = 24.dp, bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.mipmap.img_lock),
                    contentDescription = null
                )
                HeightSpacer(height = 12.dp)
                Text(
                    text = "인증번호가 발송되었습니다.\n3분 안에 인증번호를 입력해주세요.",
                    style = WineyTheme.typography.bodyB1,
                    color = WineyTheme.colors.gray_200,
                    textAlign = TextAlign.Center
                )
                HeightSpacer(height = 14.dp)
                Text(
                    text = "*인증번호 요청 3회 초과 시 5분 제한됩니다.",
                    style = WineyTheme.typography.captionM2,
                    color = WineyTheme.colors.gray_600
                )
                HeightSpacer(height = 26.dp)
                WButton(
                    text = "닫기",
                    onClick = { onConfirm() }
                )
            }
        }

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .navigationBarsPadding()
            .imePadding()
    ) {
        SignUpTopBar {
            onBack()
        }
        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            Text(
                text = "휴대폰 번호를 입력해주세요",
                color = WineyTheme.colors.gray_50,
                style = WineyTheme.typography.title1
            )
            HeightSpacer(54.dp)
            Text(
                text = if (errorState) "올바른 번호를 입력해주세요." else "전화번호",
                color = if (errorState) WineyTheme.colors.error else WineyTheme.colors.gray_600,
                style = WineyTheme.typography.bodyB2
            )
            HeightSpacer(10.dp)
            WTextField(
                value = phoneNumber,
                onValueChanged = { phoneNumber = it },
                placeholderText = "11자리 입력",
                maxLength = 11,
                visualTransformation = PhoneNumberVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onErrorState = errorState
            )
            Spacer(modifier = Modifier.weight(1f))
            WButton(
                text = "확인",
                onClick = {
                    // TODO : Add next button Event
                    showSheet = true
                },
                enabled = phoneNumber.length == 11,
                modifier = Modifier.padding(bottom = 20.dp)
            )
        }
    }
}
