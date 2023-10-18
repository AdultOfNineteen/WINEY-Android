package com.teamwiney.notedetail.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.teamwiney.ui.theme.WineyTheme


@Composable
fun NoteFeatureText(
    name: String,
) {
    Text(
        text = name,
        color = WineyTheme.colors.gray_700,
        style = WineyTheme.typography.captionB1,
        modifier = Modifier
            .clip(RoundedCornerShape(40.dp))
            .border(
                BorderStroke(
                    1.dp, WineyTheme.colors.gray_700
                ),
                RoundedCornerShape(40.dp)
            )
            .padding(10.dp)
    )
}