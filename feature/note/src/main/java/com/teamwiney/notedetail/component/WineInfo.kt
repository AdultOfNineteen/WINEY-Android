@file:OptIn(ExperimentalFoundationApi::class)

package com.teamwiney.notedetail.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.teamwiney.core.common.model.WineType
import com.teamwiney.data.network.model.response.TastingNoteDetail
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.detail.DetailPageIndicator
import com.teamwiney.ui.theme.WineyTheme


@Composable
fun WineInfo(
    userNickname: String? = null,
    tastingNoteDetail: TastingNoteDetail
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        val pageCount = 2
        val pagerState = rememberPagerState(pageCount = { pageCount })

        HorizontalPager(state = pagerState) { page ->
            val animatedProgress = remember { Animatable(0f) }

            LaunchedEffect(pagerState.currentPage) {
                animatedProgress.snapTo(0f)
                animatedProgress.animateTo(1f, tween(durationMillis = 1000))
            }

            when (page) {
                0 -> {
                    WineInfoTotalBarGraph(
                        progress = animatedProgress.value,
                        label = userNickname?.let { "${userNickname}가 느낀 와인의 맛" } ?: "내가 느낀 와인의 맛",
                        labelColor = WineyTheme.colors.main_2,
                        data = listOf(
                            Pair("당도", tastingNoteDetail.myWineTaste.sweetness),
                            Pair("산도", tastingNoteDetail.myWineTaste.acidity),
                            Pair("바디", tastingNoteDetail.myWineTaste.body),
                            Pair("탄닌", tastingNoteDetail.myWineTaste.tannin),
                            if (WineType.typeOf(tastingNoteDetail.wineType) == WineType.SPARKLING) {
                                Pair("탄산감", tastingNoteDetail.myWineTaste.alcohol)
                            } else {
                                Pair("알코올", tastingNoteDetail.myWineTaste.alcohol)
                            },
                            Pair("여운", tastingNoteDetail.myWineTaste.finish)
                        )
                    )
                }
                else -> {
                    WineInfoTotalBarGraph(
                        progress = animatedProgress.value,
                        label = "와인의 기본 맛",
                        labelColor = WineyTheme.colors.point_1,
                        data = listOf(
                            Pair("당도", tastingNoteDetail.defaultWineTaste.sweetness),
                            Pair("산도", tastingNoteDetail.defaultWineTaste.acidity),
                            Pair("바디", tastingNoteDetail.defaultWineTaste.body),
                            Pair("탄닌", tastingNoteDetail.defaultWineTaste.tannin),
                            if (WineType.typeOf(tastingNoteDetail.wineType) == WineType.SPARKLING) {
                                Pair("탄산감", tastingNoteDetail.myWineTaste.alcohol)
                            } else {
                                Pair("알코올", tastingNoteDetail.myWineTaste.alcohol)
                            },
                            Pair("여운", 0)
                        )
                    )
                }
            }

        }

        HeightSpacer(height = 50.dp)

        DetailPageIndicator(pagerState = pagerState, pageCount = pageCount)
    }
}

