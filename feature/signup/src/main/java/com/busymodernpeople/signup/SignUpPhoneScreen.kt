package com.busymodernpeople.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.PhoneNumberVisualTransformation
import com.teamwiney.ui.components.SignUpTopBar
import com.teamwiney.ui.components.WButton
import com.teamwiney.ui.components.WTextField
import com.teamwiney.ui.theme.WineyTheme

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
                    onConfirm()
                },
                enabled = phoneNumber.length == 11,
                modifier = Modifier.padding(bottom = 20.dp)
            )
        }
    }
}

