@file:OptIn(ExperimentalLayoutApi::class)

package com.teamwiney.notecollection.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.teamwiney.core.design.R
import com.teamwiney.notecollection.WineFilter
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.HeightSpacerWithLine
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun NoteBottomSheet(
    wineTypeFilter: WineFilter,
    selectedFilter: List<String>,
    onSelectedFilter: (String) -> Unit,
    onResetFilter: () -> Unit,
    onApplyFilter: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(600.dp)
            .background(WineyTheme.colors.gray_950)
            .padding(
                top = 10.dp,
                bottom = 20.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .width(66.dp)
                .height(5.dp)
                .background(
                    color = WineyTheme.colors.gray_900,
                    shape = RoundedCornerShape(6.dp)
                )
        )
        HeightSpacer(height = 20.dp)

        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            repeat(2) {
                FilterItems(
                    wineTypeFilter = wineTypeFilter,
                    selectedFilter = selectedFilter
                ) {
                    onSelectedFilter(it)
                }
            }
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
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(3.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        onResetFilter()
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
                    text = "옵션 적용하기",
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .clickable {
                            if (selectedFilter.isNotEmpty()) {
                                onApplyFilter()
                            }
                        }
                        .background(
                            color = if (selectedFilter.isEmpty()) WineyTheme.colors.gray_900 else WineyTheme.colors.main_2,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(horizontal = 72.dp, vertical = 16.dp),
                    color = if (selectedFilter.isEmpty()) WineyTheme.colors.gray_600 else WineyTheme.colors.gray_50,
                    style = WineyTheme.typography.bodyB2
                )
            }
        }

    }
}

@Composable
private fun FilterItems(
    wineTypeFilter: WineFilter,
    selectedFilter: List<String>,
    onSelectedFilter: (String) -> Unit,
) {
    Column {
        Text(
            text = wineTypeFilter.title,
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
            wineTypeFilter.filterGroup.forEach {
                val isEnable = selectedFilter.contains(it)
                Text(
                    text = buildAnnotatedString {
                        append(it)
                        append(" ")
                        withStyle(
                            style = SpanStyle(
                                color = if (isEnable) WineyTheme.colors.main_2 else WineyTheme.colors.gray_500
                            )
                        ) {
                            append("100+")
                        }
                    },
                    color = if (isEnable) WineyTheme.colors.main_2 else WineyTheme.colors.gray_700,
                    style = WineyTheme.typography.captionB1,
                    modifier = Modifier
                        .clip(RoundedCornerShape(40.dp))
                        .clickable {
                            onSelectedFilter(it)
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