package com.teamwiney.notewrite

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.navigation.NoteDestinations
import com.teamwiney.core.common.rememberWineyAppState
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.TopBar
import com.teamwiney.ui.components.WButton
import com.teamwiney.ui.theme.WineyTheme


@Preview
@Composable
fun NoteWineInfoFlavorScreen(
    appState: WineyAppState = rememberWineyAppState(),
    viewModel: NoteWriteViewModel = hiltViewModel(),
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WineyTheme.colors.background_1)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        TopBar(content = "와인 정보 입력") {
            appState.navController.navigateUp()
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 20.dp)
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "와인 맛은 어떠셨나요?",
                    style = WineyTheme.typography.bodyB1,
                    color = WineyTheme.colors.gray_50,
                )
                Text(
                    text = "향표현이 어려워요!",
                    style = WineyTheme.typography.captionM2,
                    color = WineyTheme.colors.gray_500,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable {
                        appState.navController.navigate(NoteDestinations.Write.INFO_STANDARD_FLAVOR)
                    }
                )
            }
            HeightSpacer(30.dp)
            // TODO 동천의 슬라이드
            Text(
                text = "산도",
                style = WineyTheme.typography.bodyB1,
                color = WineyTheme.colors.gray_500
            )
        }


        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(top = 20.dp, bottom = 40.dp),
        ) {
            WButton(
                text = "다음",
                modifier = Modifier
                    .weight(3f),
                enableBackgroundColor = WineyTheme.colors.main_2,
                disableBackgroundColor = WineyTheme.colors.gray_900,
                disableTextColor = WineyTheme.colors.gray_600,
                enableTextColor = WineyTheme.colors.gray_50,
                onClick = {
                    appState.navController.navigate(NoteDestinations.Write.INFO_MEMO)
                }
            )
        }
    }
}

