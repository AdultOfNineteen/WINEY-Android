package com.teamwiney.notecollection

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import com.teamwiney.core.common.AmplitudeEvent
import com.teamwiney.core.common.AmplitudeProvider
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.WineyBottomSheetState
import com.teamwiney.core.common.navigation.HomeDestinations
import com.teamwiney.core.common.navigation.NoteDestinations
import com.teamwiney.core.common.`typealias`.SheetContent
import com.teamwiney.core.design.R
import com.teamwiney.notecollection.components.EmptyNote
import com.teamwiney.notecollection.components.NoteFilterDefaultItem
import com.teamwiney.notecollection.components.NoteFilterResetButton
import com.teamwiney.notecollection.components.NoteSelectedFilterChip
import com.teamwiney.notecollection.components.NoteSortBottomSheet
import com.teamwiney.notecollection.components.NoteWineCard
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.HeightSpacerWithLine
import com.teamwiney.ui.components.home.HomeLogo
import com.teamwiney.ui.theme.WineyTheme
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NoteScreen(
    appState: WineyAppState,
    viewModel: NoteViewModel = hiltViewModel(),
    bottomSheetState: WineyBottomSheetState
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val effectFlow = viewModel.effect

    val tastingNotes = uiState.tastingNotes.collectAsLazyPagingItems()
    val tastingNotesRefreshState = tastingNotes.loadState.refresh

    BackHandler {
        if (bottomSheetState.bottomSheetState.isVisible) {
            bottomSheetState.hideBottomSheet()
        } else {
            appState.navController.navigateUp()
        }
    }

    LaunchedEffect(tastingNotesRefreshState) {
        if (tastingNotesRefreshState is LoadState.Error) {
            val errorMessage = tastingNotesRefreshState.error.message ?: "네트워크 오류가 발생했습니다."
            appState.showSnackbar(errorMessage)
        }
    }

    LaunchedEffect(true) {
        viewModel.getTastingNotes()

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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WineyTheme.colors.background_1)
    ) {
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
            Text(
                modifier = Modifier.padding(horizontal = 24.dp),
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(WineyTheme.colors.main_3)) {
                        append("${uiState.tastingNotesCount}개")
                    }
                    append("의 노트를 작성했어요!")
                },
                color = WineyTheme.colors.gray_50,
                style = WineyTheme.typography.headline
            )
            HeightSpacer(height = 14.dp)

            NoteFilterSection(
                uiState = uiState,
                viewModel = viewModel,
                hideBottomSheet = bottomSheetState::hideBottomSheet,
                showBottomSheet = bottomSheetState::showBottomSheet
            )

            if (uiState.tastingNotesCount == 0) {
                if (uiState.isLoading) {
                    SkeletonNote()
                } else {
                    EmptyNote()
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 15.dp),
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
                            onClick = {
                                val isShared = false
                                appState.navigate("${NoteDestinations.NOTE_DETAIL}?id=${it.id}&isShared=$isShared")
                            }
                        )
                    }
                }
            }
        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 34.dp, end = 24.dp),
            shape = CircleShape,
            containerColor = WineyTheme.colors.main_2,
            contentColor = WineyTheme.colors.gray_50,
            onClick = {
                appState.navigate(NoteDestinations.Write.ROUTE)
                AmplitudeProvider.trackEvent(AmplitudeEvent.NOTE_CREATE_BUTTON_CLICK)
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_pencil_29),
                contentDescription = "IC_PENCIL",
                modifier = Modifier.size(29.dp),
                tint = WineyTheme.colors.gray_50
            )
        }
    }

}

@Composable
private fun SkeletonNote() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 15.dp),
        verticalArrangement = Arrangement.spacedBy(21.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(
            count = 4,
        ) { index ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(7.dp))
                        .border(
                            BorderStroke(
                                1.dp, brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF9671FF),
                                        Color(0x109671FF),
                                    )
                                )
                            ),
                            RoundedCornerShape(7.dp)
                        )
                        .background(WineyTheme.colors.gray_950)
                )
            }
        }
    }
}

@Composable
fun NoteFilterSection(
    uiState: NoteContract.State,
    viewModel: NoteViewModel,
    hideBottomSheet: () -> Unit,
    showBottomSheet: (SheetContent) -> Unit
) {
    Column {
        HeightSpacerWithLine(
            modifier = Modifier.padding(bottom = 10.dp),
            color = WineyTheme.colors.gray_900
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                contentPadding = PaddingValues(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                if (uiState.buyAgainSelected ||
                    uiState.selectedTypeFilter.isNotEmpty() ||
                    uiState.selectedCountryFilter.isNotEmpty()
                ) {
                    item {
                        NoteFilterResetButton {
                            viewModel.resetFilter()
                        }
                    }
                }

                item {
                    NoteFilterDefaultItem(
                        name = uiState.sortItems[uiState.selectedSort],
                        onClick = {
                            showBottomSheet {
                                NoteSortBottomSheet(
                                    sortItems = uiState.sortItems,
                                    selectedSort = uiState.sortItems[uiState.selectedSort],
                                    onSelectSort = { selectedSort ->
                                        hideBottomSheet()
                                        viewModel.updateSelectedSort(selectedSort)
                                    },
                                    applyFilter = {
                                        viewModel.processEvent(NoteContract.Event.ApplyFilter)
                                    }
                                )
                            }
                        }
                    )
                }

                if (uiState.buyAgainSelected) {
                    item {
                        NoteSelectedFilterChip(
                            name = "재구매 의사",
                            onClose = {
                                viewModel.updateBuyAgainSelected(true)
                                viewModel.getTastingNotes()
                            },
                            onClick = {
                                viewModel.processEvent(NoteContract.Event.ShowFilter)
                            }
                        )
                    }
                }

                if (uiState.selectedTypeFilter.isEmpty()) {
                    item {
                        NoteFilterDefaultItem(
                            name = "와인종류",
                            onClick = { viewModel.processEvent(NoteContract.Event.ShowFilter) }
                        )
                    }
                } else {
                    uiState.selectedTypeFilter.forEach { option ->
                        item {
                            NoteSelectedFilterChip(
                                name = option.type,
                                onClose = {
                                    viewModel.removeFilter(option.type)
                                    viewModel.processEvent(NoteContract.Event.ApplyFilter)
                                }
                            )
                        }
                    }
                }

                if (uiState.selectedCountryFilter.isEmpty()) {
                    item {
                        NoteFilterDefaultItem(
                            name = "생산지",
                            onClick = { viewModel.processEvent(NoteContract.Event.ShowFilter) }
                        )
                    }
                } else {
                    uiState.selectedCountryFilter.forEach { option ->
                        item {
                            NoteSelectedFilterChip(
                                name = option.country,
                                onClose = {
                                    viewModel.removeFilter(option.country)
                                    viewModel.processEvent(NoteContract.Event.ApplyFilter)
                                }
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier.padding(end = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(7.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(
                    modifier = Modifier
                        .width(1.dp)
                        .height(45.dp)
                        .background(color = WineyTheme.colors.gray_900)
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(WineyTheme.colors.point_1)
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
        }

        HeightSpacerWithLine(
            modifier = Modifier.padding(top = 10.dp),
            color = WineyTheme.colors.gray_900
        )
    }
}