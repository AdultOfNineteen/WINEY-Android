package com.teamwiney.notecollection.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun NoteRadioButton(
    name: String,
    isEnable: Boolean = true,
    onClick: () -> Unit
) {
    Text(
        text = name,
        color = if (isEnable) WineyTheme.colors.gray_50 else WineyTheme.colors.gray_700,
        style = WineyTheme.typography.captionB1,
        modifier = Modifier
            .clip(RoundedCornerShape(40.dp))
            .background(if (isEnable) WineyTheme.colors.main_2 else WineyTheme.colors.gray_900)
            .border(
                BorderStroke(
                    1.dp,
                    if (isEnable) WineyTheme.colors.main_2 else WineyTheme.colors.gray_700
                ),
                RoundedCornerShape(40.dp)
            )
            .padding(10.dp)
            .clickable {
                onClick()
            }
    )
}
