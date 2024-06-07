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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.navigation.NoteDestinations
import com.teamwiney.notewrite.components.WineTasteSlider
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.TopBar
import com.teamwiney.ui.components.WButton
import com.teamwiney.ui.theme.WineyTheme


@Composable
fun NoteWineInfoFlavorScreen(
    appState: WineyAppState,
    viewModel: NoteWriteViewModel,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WineyTheme.colors.background_1)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        val scrollState = rememberScrollState()
        var selectedItemCount by remember { mutableIntStateOf(0) }

        LaunchedEffect(selectedItemCount) {
            scrollState.scrollTo(999999)
        }

        TopBar(content = "와인 정보 입력") {
            appState.navController.navigateUp()
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 20.dp)
                .verticalScroll(scrollState)
                .weight(1f),
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
                    text = "맛 표현이 어려워요!",
                    style = WineyTheme.typography.captionM2,
                    color = WineyTheme.colors.gray_500,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable {
                        appState.navController.navigate(NoteDestinations.Write.INFO_STANDARD_FLAVOR)
                    }
                )
            }
            HeightSpacer(30.dp)

            WineTasteSlider(
                score = uiState.wineNote.sweetness,
                onValueChange = { viewModel.updateSweetness(it) },
                title = "당도",
                subTitle = "단맛의 정도",
            )
            HeightSpacer(30.dp)

            if (uiState.wineNote.sweetness > 0) {
                WineTasteSlider(
                    score = uiState.wineNote.acidity,
                    onValueChange = { viewModel.updateAcidity(it) },
                    title = "산도",
                    subTitle = "신맛의 정도"
                )
                HeightSpacer(30.dp)

                selectedItemCount = 1
            }

            if (uiState.wineNote.acidity > 0) {
                WineTasteSlider(
                    score = uiState.wineNote.body,
                    onValueChange = { viewModel.updateBody(it) },
                    title = "바디",
                    subTitle = "농도와 질감의 정도"
                )
                HeightSpacer(30.dp)

                selectedItemCount = 2
            }

            if (uiState.wineNote.body > 0) {
                WineTasteSlider(
                    score = uiState.wineNote.tannin,
                    onValueChange = { viewModel.updateTannin(it) },
                    title = "탄닌",
                    subTitle = "떫고 씁쓸함의 정도"
                )
                HeightSpacer(30.dp)

                selectedItemCount = 3
            }

            if (uiState.wineNote.tannin > 0) {
                WineTasteSlider(
                    score = uiState.wineNote.alcohol,
                    onValueChange = { viewModel.updateAlcohol(it) },
                    title = "알코올",
                    subTitle = "알코올 세기의 정도"
                )
                HeightSpacer(30.dp)

                selectedItemCount = 4
            }

            if (uiState.wineNote.alcohol > 0) {
                WineTasteSlider(
                    score = uiState.wineNote.finish,
                    onValueChange = { viewModel.updateFinish(it) },
                    title = "여운",
                    subTitle = "마신 후 맛과 항이 지속되는 정도"
                )
                HeightSpacer(30.dp)

                selectedItemCount = 5
            }
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
                enabled = uiState.wineNote.sweetness != 0 && uiState.wineNote.acidity != 0 && uiState.wineNote.body != 0 &&
                        uiState.wineNote.tannin != 0 && uiState.wineNote.alcohol != 0 && uiState.wineNote.finish != 0,
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

