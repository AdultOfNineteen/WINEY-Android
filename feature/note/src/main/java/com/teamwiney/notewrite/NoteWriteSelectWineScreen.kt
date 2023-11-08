package com.teamwiney.notewrite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.WineyBottomSheetState
import com.teamwiney.core.common.rememberWineyAppState
import com.teamwiney.core.common.rememberWineyBottomSheetState
import com.teamwiney.notecollection.components.NoteWineCard
import com.teamwiney.notewrite.components.NoteBackgroundSurface
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.TopBar
import com.teamwiney.ui.components.WButton
import com.teamwiney.ui.theme.WineyTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
@Preview
fun NoteWriteSelectWineScreen(
    appState: WineyAppState = rememberWineyAppState(),
    viewModel: NoteWriteViewModel = hiltViewModel(),
    wineyBottomSheetState: WineyBottomSheetState = rememberWineyBottomSheetState()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val effectFlow = viewModel.effect

    val selectedWine = uiState.selectedWine

    LaunchedEffect(true) {
        effectFlow.collectLatest { effect ->
            when (effect) {
                is NoteWriteContract.Effect.NavigateTo -> {
                    appState.navigate(effect.destination, effect.navOptions)
                }

                is NoteWriteContract.Effect.ShowSnackBar -> {
                    appState.showSnackbar(effect.message)
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .background(WineyTheme.colors.background_1)
            .navigationBarsPadding()
            .statusBarsPadding()
    ) {
        NoteBackgroundSurface(modifier = Modifier.fillMaxSize())
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            TopBar(
                content = ""
            ) {
                appState.navController.navigateUp()
            }

            Text(
                text = "해당 와인으로 노트를\n작성할까요?",
                style = WineyTheme.typography.title2.copy(color = WineyTheme.colors.gray_50),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 30.dp, bottom = 60.dp),
                textAlign = TextAlign.Center
            )

            NoteWineCard(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(0.5f)
                    .aspectRatio(1f),
                color = selectedWine.type.type,
                name = selectedWine.name,
                origin = selectedWine.country,
                onClick = {
                    // TODO 노트 작성 화면으로
                },
            )

            Text(
                modifier = Modifier
                    .padding(top = 40.dp)
                    .align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                text = selectedWine.name,
                style = WineyTheme.typography.bodyM1.copy(color = WineyTheme.colors.gray_50)
            )
            HeightSpacer(height = 6.dp)
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                text = selectedWine.country,
                style = WineyTheme.typography.captionB1.copy(color = WineyTheme.colors.gray_700)
            )

            Spacer(modifier = Modifier.weight(1f))
            WButton(
                modifier = Modifier
                    .padding(bottom = 100.dp)
                    .padding(horizontal = 76.dp)
                    .align(Alignment.CenterHorizontally),
                text = "노트 작성하기",
                enableTextStyle = WineyTheme.typography.bodyB2.copy(color = WineyTheme.colors.gray_50),
                disableTextStyle = WineyTheme.typography.bodyB2.copy(color = WineyTheme.colors.gray_50),
                shape = RoundedCornerShape(46.dp),
                onClick = {
                    // TODO 노트 작성 화면으로
                }
            )
        }
    }
}

