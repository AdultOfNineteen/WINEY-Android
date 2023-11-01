package com.teamwiney.notewrite

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.WineyBottomSheetState
import com.teamwiney.core.common.navigation.NoteDestinations
import com.teamwiney.core.common.rememberWineyAppState
import com.teamwiney.core.common.rememberWineyBottomSheetState
import com.teamwiney.notewrite.components.WineSearchTextField
import com.teamwiney.ui.components.TopBar
import com.teamwiney.ui.theme.WineyTheme

@Composable
@Preview
fun NoteWineSearchScreen(
    appState: WineyAppState = rememberWineyAppState(),
    wineyBottomSheetState: WineyBottomSheetState = rememberWineyBottomSheetState()
) {
    var text by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WineyTheme.colors.background_1)
    ) {
        TopBar(
            content = "와인 검색하기"
        ) {
            appState.navController.navigateUp()
        }

        WineSearchTextField(
            modifier = Modifier.padding(horizontal = 24.dp),
            value = text,
            onValueChange = { text = it },
            placeholderText = "기록할 와인을 검색해주세요."
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 20.dp, horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(5) {
                Text(
                    text = "와인 검색 결과",
                    modifier = Modifier.clickable {
                        appState.navigate(NoteDestinations.Write.SELECT_WINE)
                    },
                    style = WineyTheme.typography.bodyM1.copy(color = WineyTheme.colors.gray_600),
                )
            }
        }
    }
}