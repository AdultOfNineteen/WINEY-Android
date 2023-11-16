package com.teamwiney.notedetail.component

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwiney.ui.theme.WineyTheme


@Composable
@Preview
fun NoteFeatureText(
    name: String = "테스트",
    enable: Boolean = false,
    onClick: () -> Unit = {}
) {
    Text(
        text = name,
        color = if (enable) WineyTheme.colors.gray_50 else WineyTheme.colors.gray_700,
        style = WineyTheme.typography.captionB1,
        modifier = Modifier
            .clip(RoundedCornerShape(40.dp))
            .border(
                BorderStroke(
                    1.dp, if (enable) WineyTheme.colors.main_2 else WineyTheme.colors.gray_700
                ),
                RoundedCornerShape(40.dp)
            )
            .background(if (enable) WineyTheme.colors.main_2 else Color.Transparent)
            .clickable {
                onClick()
            }
            .padding(10.dp)

    )
}