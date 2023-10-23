package com.teamwiney.notedetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.teamwiney.core.common.`typealias`.SheetContent
import com.teamwiney.ui.theme.WineyTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun NoteDetailBottomSheet(
    showBottomSheet: (SheetContent) -> Unit,
    hideBottomSheet: () -> Unit,
    deleteNote: () -> Unit,
    patchNote: () -> Unit
) {

    val localScope = rememberCoroutineScope()

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
                start = 24.dp,
                end = 24.dp,
                top = 10.dp,
                bottom = 20.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "삭제하기",
            color = WineyTheme.colors.gray_50,
            style = WineyTheme.typography.bodyB1,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    localScope.launch {
                        hideBottomSheet()
                        delay(100L)
                        showBottomSheet {
                            NoteDeleteBottomSheet(
                                onConfirm = deleteNote,
                                onCancel = hideBottomSheet
                            )
                        }
                    }
                }
                .padding(20.dp)
        )
        Text(
            text = "수정하기",
            color = WineyTheme.colors.gray_50,
            style = WineyTheme.typography.bodyB1,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    patchNote()
                }
                .padding(20.dp)
        )
    }
}
