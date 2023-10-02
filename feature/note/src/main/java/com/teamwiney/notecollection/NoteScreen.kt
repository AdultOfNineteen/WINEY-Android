package com.teamwiney.notecollection

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun NoteScreen(
    showBottomSheet: (SheetContent) -> Unit,
    hideBottomSheet: () -> Unit,
    viewModel: NoteViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WineyTheme.colors.background_1)
    ) {
        HomeLogo(
            onClick = {
//                viewModel.processEvent(HomeContract.Event.AnalysisButtonClicked)
            },
            hintPopupOpen = false
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(WineyTheme.colors.main_3)) {
                        append("${uiState.wineList.size}개")
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

            // TODO 와인 리스트 
            if (uiState.wineList.isEmpty()) {
                EmptyNote()
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                    ) {
                        uiState.sortedGroup.forEach { radioButton ->
                            NoteRadioButton(
                                name = radioButton,
                                isEnable = uiState.selectedSort == radioButton,
                                onClick = {
                                    viewModel.updateSelectedSort(radioButton)
                                }
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(WineyTheme.colors.gray_900)
                            .clickable {
                                showBottomSheet {
                                    NoteBottomSheet(
                                        wineTypeFilter = uiState.wineTypeFilter,
                                        selectedFilter = uiState.selectedFilter,
                                        onSelectedFilter = viewModel::updateSelectedFilter,
                                        onResetFilter = viewModel::resetFilter,
                                        onApplyFilter = {
                                            hideBottomSheet()
                                            viewModel.updateWineList()
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
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(21.dp),
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    items(uiState.wineList) {
                        NoteWineCard(wine = it)
                    }
                }
            }
        }
    }
}