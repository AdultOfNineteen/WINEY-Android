package com.teamwiney.notedetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.theme.WineyTheme


@Composable
fun NoteDetailBottomSheet(
    deleteNote: () -> Unit,
    patchNote: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = WineyTheme.colors.gray_950,
                shape = RoundedCornerShape(
                    topStart = 6.dp,
                    topEnd = 6.dp
                )
            )
            .padding(
                top = 10.dp,
                bottom = 20.dp
            ),
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
        HeightSpacer(height = 10.dp)
        Text(
            text = "삭제하기",
            color = WineyTheme.colors.gray_50,
            style = WineyTheme.typography.bodyB1,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { deleteNote() }
                .padding(
                    horizontal = 24.dp,
                    vertical = 20.dp
                )
        )
        Spacer(modifier = Modifier.fillMaxWidth().background(WineyTheme.colors.gray_900))
        Text(
            text = "수정하기",
            color = WineyTheme.colors.gray_50,
            style = WineyTheme.typography.bodyB1,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    patchNote()
                }
                .padding(
                    horizontal = 24.dp,
                    vertical = 20.dp
                )
        )
    }
}
