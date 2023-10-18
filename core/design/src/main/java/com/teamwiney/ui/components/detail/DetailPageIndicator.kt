package com.teamwiney.ui.components.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.teamwiney.ui.theme.WineyTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailPageIndicator(
    pagerState: PagerState,
    pageCount: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            space = 10.dp,
            alignment = Alignment.CenterHorizontally
        )
    ) {
        repeat(pageCount) { iteration ->
            val color = if (pagerState.currentPage == iteration) {
                WineyTheme.colors.point_1
            } else {
                WineyTheme.colors.gray_900
            }

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(color)
                    .size(10.dp)
            )
        }
    }
}