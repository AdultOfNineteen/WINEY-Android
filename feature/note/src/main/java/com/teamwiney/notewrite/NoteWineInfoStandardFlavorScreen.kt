package com.teamwiney.notewrite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.rememberWineyAppState
import com.teamwiney.notewrite.components.WineTasteSlider
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.TopBar
import com.teamwiney.ui.theme.WineyTheme


@Preview
@Composable
fun NoteWineInfoStandardFlavorScreen(
    appState: WineyAppState = rememberWineyAppState(),
    viewModel: NoteWriteViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.getSelectWineFlavor()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WineyTheme.colors.background_1)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        TopBar {
            appState.navController.navigateUp()
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "이 와인은\n이런 맛을 느껴볼 수 있어요.",
                color = WineyTheme.colors.gray_50,
                style = WineyTheme.typography.title2
            )
            HeightSpacer(height = 30.dp)
            WineTasteSlider(
                score = uiState.selectedWineInfo.sweetness,
                title = "당도",
                subTitle = "단맛의 정도",
                activeColor = WineyTheme.colors.gray_500
            )
            HeightSpacer(30.dp)
            WineTasteSlider(
                score = uiState.selectedWineInfo.acidity,
                title = "산도",
                subTitle = "신맛의 정도",
                activeColor = WineyTheme.colors.gray_500
            )
            HeightSpacer(30.dp)
            WineTasteSlider(
                score = uiState.selectedWineInfo.body,
                title = "바디",
                subTitle = "농도와 질감의 정도",
                activeColor = WineyTheme.colors.gray_500
            )
            HeightSpacer(30.dp)
            WineTasteSlider(
                score = uiState.selectedWineInfo.tannins,
                title = "탄닌",
                subTitle = "떫고 씁쓸함의 정도",
                activeColor = WineyTheme.colors.gray_500
            )
            HeightSpacer(30.dp)
        }
    }
}
