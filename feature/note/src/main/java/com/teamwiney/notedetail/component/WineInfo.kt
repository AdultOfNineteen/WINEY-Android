@file:OptIn(ExperimentalFoundationApi::class)

package com.teamwiney.notedetail.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.teamwiney.data.network.model.response.TastingNoteDetail
import com.teamwiney.ui.components.detail.DetailPageIndicator
import com.teamwiney.ui.components.detail.WineInfoBarGraph


@Composable
fun WineInfo(tastingNoteDetail: TastingNoteDetail) {

    val wine = tastingNoteDetail.getWine()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(421.dp)
    ) {
        val pageCount = 5
        val pagerState = rememberPagerState(pageCount = { pageCount })
        val tasteList = listOf(
            Triple(
                "당도",
                tastingNoteDetail.myWineTaste.sweetness.toInt(),
                tastingNoteDetail.defaultWineTaste.sweetness.toInt()
            ),
            Triple(
                "산도",
                tastingNoteDetail.myWineTaste.acidity.toInt(),
                tastingNoteDetail.defaultWineTaste.acidity.toInt()
            ),
            Triple(
                "바디",
                tastingNoteDetail.myWineTaste.body.toInt(),
                tastingNoteDetail.defaultWineTaste.body.toInt()
            ),
            Triple(
                "탄닌",
                tastingNoteDetail.myWineTaste.tannin.toInt(),
                tastingNoteDetail.defaultWineTaste.tannin.toInt()
            )
        )

        HorizontalPager(state = pagerState) { page ->
            val animatedProgress = remember { Animatable(0f) }

            LaunchedEffect(pagerState.currentPage) {
                animatedProgress.snapTo(0f)
                animatedProgress.animateTo(1f, tween(durationMillis = 1000))
            }

            if (page == 0) {
                WineInfoTotalBarGraph(
                    progress = animatedProgress.value,
                    wine = wine
                )
            } else {
                WineInfoBarGraph(
                    progress = animatedProgress.value,
                    taste = tasteList[page - 1].first,
                    default = tasteList[page - 1].second,
                    similar = tasteList[page - 1].third
                )
            }
        }

        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        )

        DetailPageIndicator(pagerState = pagerState, pageCount = pageCount)
    }
}

