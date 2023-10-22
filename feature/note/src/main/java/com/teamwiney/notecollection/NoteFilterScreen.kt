@file:OptIn(ExperimentalLayoutApi::class)

package com.teamwiney.notecollection

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.design.R
import com.teamwiney.data.network.model.response.WineCountry
import com.teamwiney.data.network.model.response.WineTypeResponse
import com.teamwiney.ui.components.HeightSpacerWithLine
import com.teamwiney.ui.components.TopBar
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun NoteFilterScreen(
    appState: WineyAppState,
    viewModel: NoteViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val selectedOptions = mutableListOf<String>().apply {
        if (uiState.buyAgainSelected) add("재구매 의사")
        addAll(uiState.selectedTypeFilter.map { it.type })
        addAll(uiState.selectedCountryFilter.map { it.country })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WineyTheme.colors.background_1)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(
                top = 10.dp,
                bottom = 20.dp
            )
    ) {
        TopBar(
            content = "필터"
        ) {
            appState.navController.navigateUp()
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxHeight()
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            BuyAgainItem(
                selectedBuyAgain = uiState.buyAgainSelected,
                onSelectBuyAgain = viewModel::updateBuyAgainSelected
            )

            TypeFilterItems(
                filterGroup = uiState.typeFilter,
                selectedFilter = uiState.selectedTypeFilter,
                onSelectFilter = {
                    viewModel.updateSelectedTypeFilter(it)
                }
            )

            CountryFilterItems(
                filterGroup = uiState.countryFilter,
                selectedFilter = uiState.selectedCountryFilter,
                onSelectFilter = {
                    viewModel.updateSelectedCountryFilter(it)
                }
            )
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            HeightSpacerWithLine(
                color = WineyTheme.colors.gray_900,
                modifier = Modifier.padding(bottom = 20.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(3.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        viewModel.resetFilter()
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_refresh_20),
                        contentDescription = "IC_REFERSH",
                        tint = WineyTheme.colors.gray_50,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "필터 초기화",
                        style = WineyTheme.typography.bodyB2,
                        color = WineyTheme.colors.gray_50
                    )
                }

                Text(
                    text = if (selectedOptions.isNotEmpty()) {
                        "${selectedOptions.size}개 옵션 적용하기"
                    } else {
                        "옵션 적용하기"
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .clickable {
                            if (selectedOptions.isNotEmpty()) {
                                viewModel.processEvent(NoteContract.Event.ApplyFilter)
                                appState.navController.navigateUp()
                            }
                        }
                        .background(
                            color = if (selectedOptions.isEmpty()) {
                                WineyTheme.colors.gray_900
                            } else {
                                WineyTheme.colors.main_2
                            },
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(vertical = 16.dp),
                    color = WineyTheme.colors.gray_50,
                    style = WineyTheme.typography.bodyB2.copy(textAlign = TextAlign.Center)
                )
            }
        }

    }
}

@Composable
private fun BuyAgainItem(
    selectedBuyAgain: Boolean,
    onSelectBuyAgain: (Boolean) -> Unit
) {
    Column {
        Text(
            text = "재구매 의사",
            color = WineyTheme.colors.gray_400,
            style = WineyTheme.typography.bodyB2
        )
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = "재구매 의사",
                color = if (selectedBuyAgain) WineyTheme.colors.main_2 else WineyTheme.colors.gray_700,
                style = WineyTheme.typography.captionB1,
                modifier = Modifier
                    .clip(RoundedCornerShape(40.dp))
                    .clickable {
                        onSelectBuyAgain(selectedBuyAgain)
                    }
                    .border(
                        BorderStroke(
                            1.dp,
                            if (selectedBuyAgain) WineyTheme.colors.main_2 else WineyTheme.colors.gray_900
                        ),
                        RoundedCornerShape(40.dp)
                    )
                    .padding(10.dp)
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TypeFilterItems(
    filterGroup: List<WineTypeResponse>,
    selectedFilter: List<WineTypeResponse>,
    onSelectFilter: (WineTypeResponse) -> Unit,
) {
    Column {
        Text(
            text = "와인 타입",
            color = WineyTheme.colors.gray_400,
            style = WineyTheme.typography.bodyB2
        )
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            filterGroup.forEach {
                val isEnable = selectedFilter.contains(it)
                Text(
                    text = buildAnnotatedString {
                        append(it.type)
                        append(" ")
                        withStyle(
                            style = SpanStyle(
                                color = if (isEnable) WineyTheme.colors.main_2 else WineyTheme.colors.gray_500
                            )
                        ) {
                            append(if (it.count.toInt() > 100) "100+" else it.count)
                        }
                    },
                    color = if (isEnable) WineyTheme.colors.main_2 else WineyTheme.colors.gray_700,
                    style = WineyTheme.typography.captionB1,
                    modifier = Modifier
                        .clip(RoundedCornerShape(40.dp))
                        .clickable {
                            onSelectFilter(it)
                        }
                        .border(
                            BorderStroke(
                                1.dp,
                                if (isEnable) WineyTheme.colors.main_2 else WineyTheme.colors.gray_900
                            ),
                            RoundedCornerShape(40.dp)
                        )
                        .padding(10.dp)
                )
            }
        }
    }
}

@Composable
private fun CountryFilterItems(
    filterGroup: List<WineCountry>,
    selectedFilter: List<WineCountry>,
    onSelectFilter: (WineCountry) -> Unit,
) {
    Column {
        Text(
            text = "생산지",
            color = WineyTheme.colors.gray_400,
            style = WineyTheme.typography.bodyB2
        )
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            filterGroup.forEach {
                val isEnable = selectedFilter.contains(it)
                Text(
                    text = buildAnnotatedString {
                        append(it.country)
                        append(" ")
                        withStyle(
                            style = SpanStyle(
                                color = if (isEnable) WineyTheme.colors.main_2 else WineyTheme.colors.gray_500
                            )
                        ) {
                            append(if (it.count.toInt() > 100) "100+" else it.count)
                        }
                    },
                    color = if (isEnable) WineyTheme.colors.main_2 else WineyTheme.colors.gray_700,
                    style = WineyTheme.typography.captionB1,
                    modifier = Modifier
                        .clip(RoundedCornerShape(40.dp))
                        .clickable {
                            onSelectFilter(it)
                        }
                        .border(
                            BorderStroke(
                                1.dp,
                                if (isEnable) WineyTheme.colors.main_2 else WineyTheme.colors.gray_900
                            ),
                            RoundedCornerShape(40.dp)
                        )
                        .padding(10.dp)
                )
            }
        }
    }
}
