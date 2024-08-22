package com.teamwiney.notewrite

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.teamwiney.core.common.AmplitudeEvent
import com.teamwiney.core.common.AmplitudeProvider
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.WineyBottomSheetState
import com.teamwiney.core.common.navigation.NoteDestinations
import com.teamwiney.notecollection.components.NoteWineCard
import com.teamwiney.notewrite.components.NoteBackgroundSurface
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.TopBar
import com.teamwiney.ui.components.WButton
import com.teamwiney.ui.theme.WineyTheme
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NoteWriteSelectWineScreen(
    appState: WineyAppState,
    viewModel: NoteWriteViewModel,
    bottomSheetState: WineyBottomSheetState
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val effectFlow = viewModel.effect

    val selectedWine = uiState.selectedWine

    BackHandler {
        if (bottomSheetState.bottomSheetState.isVisible) {
            bottomSheetState.hideBottomSheet()
        } else {
            appState.navController.navigateUp()
        }
    }

    LaunchedEffect(true) {
        effectFlow.collectLatest { effect ->
            when (effect) {
                is NoteWriteContract.Effect.NavigateTo -> {
                    appState.navigate(effect.destination, effect.navOptions)
                }

                is NoteWriteContract.Effect.ShowSnackBar -> {
                    appState.showSnackbar(effect.message)
                }

                else -> { }
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

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "해당 와인으로\n노트를 작성할까요?",
                    style = WineyTheme.typography.title2.copy(color = WineyTheme.colors.gray_50),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 60.dp),
                    textAlign = TextAlign.Center
                )

                NoteWineCard(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(0.4f)
                        .aspectRatio(1f),
                    color = selectedWine.type.type,
                    cardBackgroundAlpha = 0.2f,
                    name = selectedWine.name,
                    origin = selectedWine.country,
                    onClick = { },
                )
                HeightSpacer(height = 20.dp)

                Text(
                    modifier = Modifier
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

                HeightSpacer(height = 150.dp)
                WButton(
                    modifier = Modifier
                        .padding(horizontal = 76.dp)
                        .align(Alignment.CenterHorizontally),
                    text = "노트 작성하기",
                    enableTextStyle = WineyTheme.typography.bodyB2.copy(color = WineyTheme.colors.gray_50),
                    disableTextStyle = WineyTheme.typography.bodyB2.copy(color = WineyTheme.colors.gray_50),
                    shape = RoundedCornerShape(46.dp),
                    onClick = {
                        appState.navigate(NoteDestinations.Write.INFO_LEVEL)
                        AmplitudeProvider.trackEvent(AmplitudeEvent.WINE_SELECT_BUTTON_CLICK)
                    }
                )
            }

        }
    }
}

