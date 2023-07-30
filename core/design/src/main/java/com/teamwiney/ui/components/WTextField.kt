package com.teamwiney.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamwiney.ui.theme.LocalColors
import com.teamwiney.ui.theme.WineyTheme

/**
 * @param onErrorState 해당 값이 true라면 밑줄이 색이 빨개집니다.
 * @param onFocusChanged 포커스 값을 외부에서 관찰할 때 사용
 */
@Composable
fun WTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    placeholderText: String = "",
    fontSize: TextUnit = 16.sp,
    focusRequest: FocusRequester? = null,
    keyboardOptions: KeyboardOptions? = null,
    keyboardActions: KeyboardActions? = null,
    onFocusedChange: (Boolean) -> Unit = {},
    onErrorState: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    maxLength: Int = 25,
) {
    val localColors = LocalColors.current
    val bottomLineColor = remember {
        mutableStateOf(localColors.gray_800)
    }

    Column(modifier = modifier) {
        BasicTextField(
            modifier = Modifier
                .onFocusChanged {
                    onFocusedChange(it.isFocused)
                    if (it.isFocused) {
                        bottomLineColor.value = localColors.gray_50
                    } else {
                        bottomLineColor.value = localColors.gray_800
                    }
                }
                .bottomBorder(1.dp, if (onErrorState) localColors.error else bottomLineColor.value)
                .focusRequester(focusRequest ?: FocusRequester()),
            value = value,
            onValueChange = {
                if (it.length <= maxLength) onValueChanged(it)
            },
            singleLine = true,
            cursorBrush = SolidColor(Color.White),
            textStyle = WineyTheme.typography.bodyM1.copy(color = localColors.gray_50),
            keyboardOptions = keyboardOptions ?: KeyboardOptions(),
            keyboardActions = keyboardActions ?: KeyboardActions(),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(0.dp, 9.dp)
                ) {
                    if (leadingIcon != null) leadingIcon()
                    Box(Modifier.weight(1f)) {
                        if (value.isEmpty()) {
                            Text(
                                placeholderText,
                                style = LocalTextStyle.current.copy(
                                    color = localColors.gray_800,
                                    fontSize = fontSize,
                                ),
                            )
                        }
                        innerTextField()
                    }
                    if (trailingIcon != null) trailingIcon()
                }
            },
            visualTransformation = visualTransformation,
        )
    }
}