package com.teamwiney.notecollection.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.teamwiney.core.design.R
import com.teamwiney.ui.theme.WineyTheme


@Composable
fun ColumnScope.EmptyNote() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(
                14.dp,
                Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.mipmap.img_note_empty),
                contentDescription = "IMG_NOTE_EMPTY"
            )
            Text(
                text = "아직 노트가 없어요!\n노트를 작성해 주세요:)",
                style = WineyTheme.typography.headline,
                color = WineyTheme.colors.gray_800,
                textAlign = TextAlign.Center
            )
        }
    }
}