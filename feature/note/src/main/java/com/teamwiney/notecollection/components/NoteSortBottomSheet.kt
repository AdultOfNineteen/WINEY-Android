package com.teamwiney.notecollection.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun NoteSortBottomSheet(
    modifier: Modifier = Modifier,
    sortItems: List<String>,
    selectedSort: String,
    onSelectSort: (String) -> Unit,
    applyFilter: () -> Unit
) {
    var selectedSortType by remember { mutableStateOf(selectedSort) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = WineyTheme.colors.gray_950,
                shape = RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp)
            )
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

        sortItems.forEachIndexed { index, sort ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        selectedSortType = sort
                        onSelectSort(sort)
                        applyFilter()
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.padding(
                        start = 24.dp,
                        top = 20.dp,
                        bottom = 20.dp
                    ),
                    text = sort,
                    style = WineyTheme.typography.bodyB1.copy(
                        color = WineyTheme.colors.gray_50
                    )
                )

                if (selectedSortType == sort) {
                    Icon(
                        modifier = Modifier.padding(end = 24.dp),
                        painter = painterResource(id = com.teamwiney.core.design.R.drawable.ic_arrow_down),
                        contentDescription = "IC_ARROW_DOWN",
                        tint = WineyTheme.colors.gray_50
                    )
                }
            }

            if (index != sortItems.size - 1) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .height(1.dp)
                        .background(
                            color = WineyTheme.colors.gray_900
                        )
                )
            }
        }
    }
}