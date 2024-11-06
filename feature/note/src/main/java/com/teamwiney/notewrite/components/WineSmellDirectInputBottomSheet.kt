package com.teamwiney.notewrite.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.WButton
import com.teamwiney.ui.components.WTextField
import com.teamwiney.ui.theme.WineyTheme

const val SMELL_KEYWORD_LENGTH = 7

@Composable
fun WineSmellDirectInputBottomSheet(
    modifier: Modifier = Modifier,
    containerColor: Color = WineyTheme.colors.gray_950,
    onConfirm: (String) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    var keyword by remember { mutableStateOf("") }

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

        HeightSpacer(height = 32.dp)

        Text(
            "향 키워드",
            style = WineyTheme.typography.bodyB2.copy(
                color = WineyTheme.colors.gray_600
            ),
            modifier = Modifier.align(alignment = Alignment.Start)
        )

        HeightSpacer(height = 4.dp)

        WTextField(
            value = keyword,
            onValueChanged = {
                keyword = it
            },
            placeholderText = "와인의 향을 입력해주세요",
            trailingIcon = {
                Text(
                    text = "${keyword.length}/$SMELL_KEYWORD_LENGTH",
                    style = WineyTheme.typography.bodyM2.copy(
                        color = WineyTheme.colors.gray_500
                    ),
                )
            },
            maxLength = SMELL_KEYWORD_LENGTH,
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
                onConfirm(keyword)
            })
        )
        
        HeightSpacer(height = 32.dp)
        
        WButton(
            text = "확인",
            onClick = {
                onConfirm(keyword)
            },
            enabled = keyword.isNotEmpty(),
            modifier = Modifier.padding(bottom = 10.dp)
        )
    }
}