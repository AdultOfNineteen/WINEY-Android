@file:OptIn(ExperimentalFoundationApi::class)

package com.teamwiney.notedetail


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.WineyBottomSheetState
import com.teamwiney.core.design.R
import com.teamwiney.notedetail.component.NoteDetailBottomSheet
import com.teamwiney.notedetail.component.WineInfo
import com.teamwiney.notedetail.component.WineMemo
import com.teamwiney.notedetail.component.WineOrigin
import com.teamwiney.notedetail.component.WineSmellFeature
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.HeightSpacerWithLine
import com.teamwiney.ui.components.TopBar
import com.teamwiney.ui.components.detail.TitleAndDescription
import com.teamwiney.ui.theme.WineyTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NoteDetailScreen(
    appState: WineyAppState,
    noteId: Int = 0,
    viewModel: NoteDetailViewModel = hiltViewModel(),
    bottomSheetState: WineyBottomSheetState
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.getNoteDetail(noteId)
        viewModel.effect.collectLatest { effect ->
            bottomSheetState.hideBottomSheet()
            when (effect) {
                is NoteDetailContract.Effect.NavigateTo -> {
                    appState.navigate(effect.destination, effect.navOptions)
                }

                is NoteDetailContract.Effect.ShowSnackBar -> {
                    appState.showSnackbar(effect.message)
                }

                is NoteDetailContract.Effect.NoteDeleted -> {
                    appState.showSnackbar("노트가 삭제되었습니다.")
                    appState.navController.navigateUp()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WineyTheme.colors.background_1)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        TopBar(
            leadingIconOnClick = {
                appState.navController.navigateUp()
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_kebab_28),
                    contentDescription = null,
                    tint = WineyTheme.colors.gray_50,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(28.dp)
                        .clickable {
                            bottomSheetState.showBottomSheet {
                                NoteDetailBottomSheet(
                                    showBottomSheet = bottomSheetState::showBottomSheet,
                                    hideBottomSheet = bottomSheetState::hideBottomSheet,
                                    deleteNote = {
                                        viewModel.deleteNote(uiState.noteDetail.noteId.toInt())
                                    },
                                    patchNote = {
                                    }
                                )
                            }
                        }
                )
            }
        )

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {

                TitleAndDescription(
                    type = uiState.noteDetail.wineType,
                    name = uiState.noteDetail.wineName
                )

                HeightSpacerWithLine(
                    modifier = Modifier.padding(vertical = 20.dp),
                    color = WineyTheme.colors.gray_900
                )

                WineOrigin(uiState.noteDetail)

                WineSmellFeature(uiState.noteDetail)

                HeightSpacerWithLine(
                    modifier = Modifier.padding(vertical = 20.dp),
                    color = WineyTheme.colors.gray_900
                )

                WineInfo(uiState.noteDetail)

                HeightSpacerWithLine(
                    modifier = Modifier.padding(vertical = 25.dp),
                    color = WineyTheme.colors.gray_900
                )
            }
            WineMemo(uiState.noteDetail)

            HeightSpacer(height = 33.dp)
        }
    }

}
