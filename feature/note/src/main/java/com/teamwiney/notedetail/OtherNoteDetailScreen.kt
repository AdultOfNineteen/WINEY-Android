package com.teamwiney.notedetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.WineyBottomSheetState
import com.teamwiney.core.common.navigation.NoteDestinations
import com.teamwiney.core.design.R
import com.teamwiney.data.network.model.response.TastingNoteDetail
import com.teamwiney.notedetail.component.NoteDeleteBottomSheet
import com.teamwiney.notedetail.component.NoteDetailBottomSheet
import com.teamwiney.notedetail.component.WineInfo
import com.teamwiney.notedetail.component.WineMemo
import com.teamwiney.notedetail.component.WineOrigin
import com.teamwiney.notedetail.component.WineSmellFeature
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.HeightSpacerWithLine
import com.teamwiney.ui.components.LoadingDialog
import com.teamwiney.ui.components.TopBar
import com.teamwiney.ui.components.detail.NoteTitleAndDescription
import com.teamwiney.ui.theme.WineyTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun OtherNoteDetailScreen(
    appState: WineyAppState,
    viewModel: NoteDetailViewModel,
    bottomSheetState: WineyBottomSheetState
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.effect.collectLatest { effect ->
            bottomSheetState.hideBottomSheet()
            when (effect) {
                is NoteDetailContract.Effect.NavigateTo -> {
                    appState.navigate(effect.destination, effect.navOptions)
                }

                is NoteDetailContract.Effect.ShowSnackBar -> {
                    appState.showSnackbar(effect.message)
                }

                is NoteDetailContract.Effect.ShowBottomSheet -> {
                    bottomSheetState.showBottomSheet {
                        when (effect.bottomSheet) {
                            is NoteDetailContract.BottomSheet.NoteOption -> {
                                NoteDetailBottomSheet(
                                    isShowShareNote = false,
                                    shareNote = { },
                                    deleteNote = {
                                        bottomSheetState.hideBottomSheet()
                                        viewModel.processEvent(
                                            NoteDetailContract.Event.ShowNoteDeleteBottomSheet
                                        )
                                    },
                                    patchNote = {
                                        bottomSheetState.hideBottomSheet()
                                        appState.navigate("${NoteDestinations.Write.ROUTE}?noteId=${uiState.noteDetail.noteId}")
                                    }
                                )
                            }

                            is NoteDetailContract.BottomSheet.NoteDelete -> {
                                NoteDeleteBottomSheet(
                                    onConfirm = {
                                        viewModel.deleteNote(uiState.noteDetail.noteId.toInt())
                                    },
                                    onCancel = {
                                        bottomSheetState.hideBottomSheet()
                                    }
                                )
                            }
                        }
                    }
                }

                is NoteDetailContract.Effect.NoteDeleted -> {
                    appState.showSnackbar("노트가 삭제되었습니다.")
                    appState.navController.navigateUp()
                }
            }
        }
    }

    if (uiState.isLoading) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WineyTheme.colors.background_1)
                .statusBarsPadding()
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBar(
                leadingIconOnClick = {
                    appState.navController.navigateUp()
                }
            )
            LoadingDialog()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WineyTheme.colors.background_1)
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .navigationBarsPadding(),
        ) {
            TopBar(
                leadingIconOnClick = {
                    appState.navController.navigateUp()
                }
            )

            Column {
                NoteTitleAndDescription(
                    number = uiState.noteDetail.tastingNoteNo,
                    userNickname = uiState.noteDetail.userNickname,
                    date = uiState.noteDetail.noteDate,
                    type = uiState.noteDetail.wineType,
                    name = uiState.noteDetail.wineName
                )

                HeightSpacerWithLine(
                    modifier = Modifier.padding(vertical = 20.dp),
                    color = WineyTheme.colors.gray_900
                )

                WineOrigin(uiState.noteDetail)

                HeightSpacerWithLine(
                    modifier = Modifier.padding(top = 20.dp),
                    color = WineyTheme.colors.gray_900
                )
            }

            NoteContent(
                userNickname = uiState.noteDetail.userNickname,
                noteDetail = uiState.noteDetail
            )
        }
    }
}

@Composable
private fun NoteContent(
    userNickname: String?,
    noteDetail: TastingNoteDetail
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HeightSpacer(height = 30.dp)

        WineSmellFeature(noteDetail)

        HeightSpacerWithLine(
            modifier = Modifier.padding(top = 38.dp, bottom = 30.dp),
            color = WineyTheme.colors.gray_900
        )

        WineInfo(
            userNickname = userNickname,
            tastingNoteDetail = noteDetail
        )

        HeightSpacerWithLine(
            modifier = Modifier.padding(top = 25.dp, bottom = 30.dp),
            color = WineyTheme.colors.gray_900
        )

        WineMemo(noteDetail)
    }
}