package com.teamwiney.noteadd.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun WineSearchTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholderText: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
    keyboardActions: KeyboardActions? = null
) {
    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        singleLine = true,
        cursorBrush = SolidColor(Color.White),
        textStyle = WineyTheme.typography.bodyM1.copy(color = WineyTheme.colors.gray_50),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions ?: KeyboardActions(),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier.background(
                    shape = CircleShape,
                    color = WineyTheme.colors.gray_900
                )
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 18.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        if (value.isEmpty()) {
                            Text(
                                text = placeholderText,
                                style = WineyTheme.typography.bodyM1.copy(
                                    color = WineyTheme.colors.gray_700
                                )
                            )
                        }
                        innerTextField()
                    }
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = com.teamwiney.core.design.R.drawable.ic_search),
                        contentDescription = "IC_SEARCH",
                        tint = Color.White
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun PreviewWineSearchTextField() {
    WineyTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WineyTheme.colors.background_1)
                .padding(horizontal = 24.dp, vertical = 20.dp)
        ) {
            var text by remember { mutableStateOf("") }

            WineSearchTextField(
                value = text,
                onValueChange = {
                    text = it
                },
                placeholderText = "기록할 와인을 검색해주세요!"
            )
        }
    }
}