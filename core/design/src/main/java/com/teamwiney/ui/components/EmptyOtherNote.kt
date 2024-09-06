package com.teamwiney.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwiney.core.design.R
import com.teamwiney.ui.theme.WineyTheme

@Preview
@Composable
fun EmptyOtherNote() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.mipmap.img_note_empty),
            contentDescription = "IMG_NOTE_EMPTY"
        )

        HeightSpacer(height = 14.dp)

        Text(
            "등록된 노트가 없어요 :(",
            style = WineyTheme.typography.headline,
            color = WineyTheme.colors.gray_800
        )
        HeightSpacer(height = 6.dp)
        Text(
            "이 와인의 첫 와이너가 되어보세요!",
            style = WineyTheme.typography.bodyM2,
            color = WineyTheme.colors.gray_800
        )
    }
}