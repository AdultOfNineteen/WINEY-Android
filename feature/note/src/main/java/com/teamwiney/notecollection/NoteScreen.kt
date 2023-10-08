package com.teamwiney.notecollection

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.navigation.HomeDestinations
import com.teamwiney.core.common.`typealias`.SheetContent
import com.teamwiney.core.design.R
import com.teamwiney.notecollection.components.EmptyNote
import com.teamwiney.notecollection.components.NoteBottomSheet
import com.teamwiney.notecollection.components.NoteRadioButton
import com.teamwiney.notecollection.components.NoteWineCard
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.HeightSpacerWithLine
import com.teamwiney.ui.components.home.HomeLogo
import com.teamwiney.ui.theme.WineyTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NoteScreen(
    appState: WineyAppState,
    showBottomSheet: (SheetContent) -> Unit,
    hideBottomSheet: () -> Unit,
    viewModel: NoteViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val effectFlow = viewModel.effect

    val tastingNotes = uiState.tastingNotes.collectAsLazyPagingItems()
    val tastingNotesRefreshState = tastingNotes.loadState.refresh

    LaunchedEffect(tastingNotesRefreshState) {
        if (tastingNotesRefreshState is LoadState.Error) {
            val errorMessage = tastingNotesRefreshState.error.message ?: "네트워크 오류가 발생했습니다."
            appState.showSnackbar(errorMessage)
        }
    }

    LaunchedEffect(true) {
        viewModel.processEvent(NoteContract.Event.ApplyFilter)

        effectFlow.collectLatest { effect ->
            when (effect) {
                is NoteContract.Effect.NavigateTo -> {
                    appState.navigate(effect.destination, effect.navOptions)
                }

                is NoteContract.Effect.ShowSnackBar -> {
                    appState.showSnackbar(effect.message)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WineyTheme.colors.background_1)
    ) {
        HomeLogo(
            onClick = {
                appState.navigate(HomeDestinations.Analysis.ROUTE)
            },
            hintPopupOpen = false
        )
        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(WineyTheme.colors.main_3)) {
                        append("${tastingNotes.itemCount}개")
                    }
                    append("의 노트를 작성했어요!")
                },
                color = WineyTheme.colors.gray_50,
                style = WineyTheme.typography.headline
            )
            HeightSpacerWithLine(
                modifier = Modifier.padding(vertical = 14.dp),
                color = WineyTheme.colors.gray_900
            )
        }

        if (tastingNotes.itemCount == 0) {
            EmptyNote()
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                ) {
                    uiState.sortedGroup.forEach { radioButton ->
                        NoteRadioButton(
                            name = radioButton,
                            isEnable = uiState.selectedSort == uiState.sortedGroup.indexOf(
                                radioButton
                            ),
                            onClick = {
                                viewModel.updateSelectedSort(radioButton)
                                viewModel.processEvent(NoteContract.Event.ApplyFilter)
                            }
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(WineyTheme.colors.gray_900)
                        .clickable {
                            viewModel.processEvent(NoteContract.Event.ShowFilter)
                            showBottomSheet {
                                NoteBottomSheet(
                                    typeFilter = uiState.typeFilter,
                                    countryFilter = uiState.countryFilter,
                                    selectedTypeFilter = uiState.selectedTypeFilter,
                                    selectedCountryFilter = uiState.selectedCountryFilter,
                                    onSelectTypeFilter = viewModel::updateSelectedTypeFilter,
                                    onSelectCountryFilter = viewModel::updateSelectedCountryFilter,
                                    onResetFilter = viewModel::resetFilter,
                                    onApplyFilter = {
                                        hideBottomSheet()
                                        viewModel.processEvent(NoteContract.Event.ApplyFilter)
                                    }
                                )
                            }
                        }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_filter_24),
                        contentDescription = "IC_FILTER",
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 5.dp)
                            .size(24.dp),
                        tint = WineyTheme.colors.gray_50
                    )
                }
            }
            HeightSpacer(height = 18.dp)
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(21.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                items(
                    count = tastingNotes.itemCount,
                    key = tastingNotes.itemKey(),
                    contentType = tastingNotes.itemContentType()
                ) { index ->
                    tastingNotes[index]?.let {
                        NoteWineCard(
                            color = it.wineType,
                            name = it.name,
                            origin = it.country,
                            starRating = it.starRating
                        )
                    }
                }
            }
        }
    }
}