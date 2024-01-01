package com.teamwiney.mypage.badge

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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.WineyBottomSheetState
import com.teamwiney.data.network.model.response.WineBadge
import com.teamwiney.mypage.MyPageContract
import com.teamwiney.mypage.MyPageViewModel
import com.teamwiney.mypage.components.MyPageBadgeDetailBottomSheet
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.HeightSpacerWithLine
import com.teamwiney.ui.components.TopBar
import com.teamwiney.ui.theme.WineyTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MyPageBadgeScreen(
    appState: WineyAppState,
    viewModel: MyPageViewModel,
    bottomSheetState: WineyBottomSheetState
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val effectFlow = viewModel.effect

    LaunchedEffect(true) {
        viewModel.getUserWineBadgeList()

        effectFlow.collectLatest { effect ->
            when (effect) {
                is MyPageContract.Effect.NavigateTo -> {
                    appState.navigate(effect.destination, effect.navOptions)
                }

                is MyPageContract.Effect.ShowSnackBar -> {
                    appState.showSnackbar(effect.message)
                }

                is MyPageContract.Effect.ShowBottomSheet -> {
                    when (effect.bottomSheet) {
                        is MyPageContract.BottomSheet.WineBadgeDetail -> {
                            bottomSheetState.showBottomSheet {
                                MyPageBadgeDetailBottomSheet(
                                    wineBadge = effect.bottomSheet.wineBadge
                                ) {
                                    bottomSheetState.hideBottomSheet()
                                }
                            }
                        }

                        else -> { }
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WineyTheme.colors.background_1)
    ) {
        TopBar(
            content = "WINEY 뱃지"
        ) {
            appState.navController.navigateUp()
        }
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            MyPageSommelierBadge(
                badges = uiState.sommelierBadges,
                onBadgeClick = viewModel::getWineBadgeDetail
            )
            HeightSpacerWithLine(color = WineyTheme.colors.gray_900)
            MyPageActivityBadge(
                badges = uiState.activityBadges,
                onBadgeClick = viewModel::getWineBadgeDetail
            )
        }
    }
}

@Composable
private fun MyPageSommelierBadge(
    badges: List<WineBadge>,
    onBadgeClick: (Long) -> Unit,
) {
    val configuration = LocalConfiguration.current
    val itemWidth = configuration.screenWidthDp.dp * 0.26f

    Column {
        HeightSpacer(height = 10.dp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = buildAnnotatedString {
                    append("WINEY")
                    withStyle(SpanStyle(color = WineyTheme.colors.main_3)) {
                        append(" 소믈리에")
                    }
                },
                style = WineyTheme.typography.headline.copy(
                    color = WineyTheme.colors.gray_50
                )
            )

            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(color = WineyTheme.colors.main_3)) {
                        append("${badges.filter { it.acquiredAt != null}.size}")
                    }
                    append("개")
                },
                style = WineyTheme.typography.headline.copy(
                    color = WineyTheme.colors.gray_50
                )
            )
        }

        LazyRow(
            modifier = Modifier.padding(vertical = 20.dp),
            contentPadding = PaddingValues(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(badges) { badge ->
                WineBadgeItem(
                    modifier = Modifier
                        .width(itemWidth)
                        .clickable {
                            onBadgeClick(badge.badgeId)
                        },
                    wineBadge = badge
                )
            }
        }
    }
}

@Composable
private fun MyPageActivityBadge(
    badges: List<WineBadge>,
    onBadgeClick: (Long) -> Unit
) {
    val configuration = LocalConfiguration.current
    val itemWidth = configuration.screenWidthDp.dp * 0.26f

    Column {
        HeightSpacer(height = 20.dp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = buildAnnotatedString {
                    append("WINEY")
                    withStyle(SpanStyle(color = WineyTheme.colors.main_3)) {
                        append(" 활동뱃지")
                    }
                },
                style = WineyTheme.typography.headline.copy(
                    color = WineyTheme.colors.gray_50
                )
            )

            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(color = WineyTheme.colors.main_3)) {
                        append("${badges.filter { it.acquiredAt != null}.size}")
                    }
                    append("개")
                },
                style = WineyTheme.typography.headline.copy(
                    color = WineyTheme.colors.gray_50
                )
            )
        }

        LazyHorizontalGrid(
            modifier = Modifier
                .padding(vertical = 20.dp)
                .heightIn(max = (itemWidth + 79.dp) * 2 + 20.dp),
            rows = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(badges) { badge ->
                WineBadgeItem(
                    modifier = Modifier
                        .width(itemWidth)
                        .clickable {
                            onBadgeClick(badge.badgeId)
                        },
                    wineBadge = badge
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun WineBadgeItem(
    modifier: Modifier = Modifier,
    wineBadge: WineBadge
) {
    Column(
        modifier = modifier.height(IntrinsicSize.Max)
    ) {
        Spacer(
            modifier = Modifier
                .size(8.dp)
                .background(
                    color = if (wineBadge.isRead == true) {
                        WineyTheme.colors.main_2
                    } else {
                        Color.Transparent
                    },
                    shape = CircleShape
                )
                .align(Alignment.End)
        )
        HeightSpacer(height = 6.dp)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .border(
                    border = BorderStroke(
                        width = 1.dp,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF9671FF),
                                Color(0xFF9671FF).copy(alpha = 0.4f)
                            )
                        )
                    ),
                    shape = RoundedCornerShape(5.dp)
                )
        ) {
            GlideImage(
                model = wineBadge.badgeImage,
                contentDescription = "BADGE_IMAGE",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(5.dp))
                    .background(WineyTheme.colors.gray_900),
                contentScale = ContentScale.Crop
            )
        }
        HeightSpacer(height = 10.dp)
        Text(
            text = wineBadge.name,
            style = WineyTheme.typography.bodyB2.copy(
                color = if (wineBadge.acquiredAt != null) {
                    WineyTheme.colors.gray_50
                } else {
                    WineyTheme.colors.gray_700
                }
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = wineBadge.acquiredAt ?: "미취득",
            style = WineyTheme.typography.captionM2.copy(
                color = WineyTheme.colors.gray_700
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}