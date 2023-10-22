package com.teamwiney.notecollection

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.teamwiney.data.network.model.response.WineCountry
import com.teamwiney.data.network.model.response.WineTypeResponse
import com.teamwiney.notecollection.components.EmptyNote
import com.teamwiney.notecollection.components.NoteSelectedFilterChip
import com.teamwiney.notecollection.components.NoteWineCard
import com.teamwiney.notecollection.components.ResetFilterButton
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.HeightSpacerWithLine
import com.teamwiney.ui.components.home.HomeLogo
import com.teamwiney.ui.theme.WineyTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SelectedFilterItems(
    modifier: Modifier = Modifier,
    selectedSort: Int,
    sortedGroup: List<String>,
    selectedTypeFilter: List<WineTypeResponse>,
    selectedCountryFilter: List<WineCountry>,
    showFilter: () -> Unit,
    resetFilter: () -> Unit
) {
    LazyRow(
        modifier = modifier.verticalScroll(rememberScrollState()),
        contentPadding = PaddingValues(end = 47.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        item {
            ResetFilterButton {
                resetFilter()
            }
        }

        item {
            NoteSelectedFilterChip(
                name = sortedGroup[selectedSort],
                isEnable = true,
                onClick = { showFilter() }
            )
        }

        item {
            NoteSelectedFilterChip(
                name = if (selectedTypeFilter.isEmpty()) {
                    "와인종류"
                } else if (selectedTypeFilter.size == 1) {
                    selectedTypeFilter[0].type
                } else {
                    "${selectedTypeFilter[0].type} 외 ${selectedTypeFilter.size - 1}"
                },
                isEnable = selectedTypeFilter.isNotEmpty(),
                onClick = { showFilter() }
            )
        }

        item {
            NoteSelectedFilterChip(
                name = if (selectedCountryFilter.isEmpty()) {
                    "생산지"
                } else if (selectedCountryFilter.size == 1) {
                    selectedCountryFilter[0].country
                } else {
                    "${selectedCountryFilter[0].country} 외 ${selectedCountryFilter.size - 1}"
                },
                isEnable = selectedCountryFilter.isNotEmpty(),
                onClick = { showFilter() }
            )
        }
    }
}


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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                SelectedFilterItems(
                    sortedGroup = uiState.sortedGroup,
                    selectedSort = uiState.selectedSort,
                    selectedTypeFilter = uiState.selectedTypeFilter,
                    selectedCountryFilter = uiState.selectedCountryFilter,
                    showFilter = {
                        viewModel.processEvent(NoteContract.Event.ShowFilter)
                    },
                    resetFilter = {
                        viewModel.resetFilter()
                        viewModel.processEvent(NoteContract.Event.ApplyFilter)
                    }
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .align(Alignment.CenterEnd)
                        .background(WineyTheme.colors.gray_900)
                        .clickable {
                            viewModel.processEvent(NoteContract.Event.ShowFilter)
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
            HeightSpacer(height = 15.dp)

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
                            starRating = it.starRating,
                            navigateToNoteDetail = { },
                        )
                    }
                }
            }
        }
    }
}