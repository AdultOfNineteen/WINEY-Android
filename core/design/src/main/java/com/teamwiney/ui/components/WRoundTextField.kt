package com.teamwiney.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.teamwiney.ui.theme.LocalColors
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun WRoundTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholderText: String = "",
    maxLength: Int = 200,
) {
    val localColors = LocalColors.current
    val borderColor = remember {
        mutableStateOf(localColors.gray_800)
    }

    Column {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .heightIn(min = 150.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(
                    BorderStroke(
                        1.dp, borderColor.value
                    ),
                    RoundedCornerShape(10.dp)
                )
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .onFocusChanged {
                        if (it.isFocused) {
                            borderColor.value = localColors.gray_50
                        } else {
                            borderColor.value = localColors.gray_800
                        }
                    }
                    .padding(14.dp)
                    .fillMaxWidth()
                    .heightIn(min = 150.dp),
                textStyle = WineyTheme.typography.captionM1.copy(
                    color = WineyTheme.colors.gray_50
                ),
                cursorBrush = SolidColor(Color.White),
                decorationBox = { innerTextField ->
                    Box {
                        if (value.isEmpty()) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = placeholderText,
                                style = LocalTextStyle.current.copy(
                                    color = WineyTheme.colors.gray_800
                                ),
                                textAlign = TextAlign.Start,
                            )
                        }
                        innerTextField()
                    }
                },
            )
        }
        Text(
            text = "${value.length}/$maxLength",
            style = WineyTheme.typography.bodyM2,
            color = WineyTheme.colors.gray_500,
            modifier = Modifier
                .padding(top = 10.dp)
                .align(Alignment.End)
        )
    }
}