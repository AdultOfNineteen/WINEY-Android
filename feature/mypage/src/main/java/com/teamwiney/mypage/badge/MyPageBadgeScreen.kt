package com.teamwiney.mypage.badge

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.model.BadgeType
import com.teamwiney.data.network.model.response.WineBadge
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.HeightSpacerWithLine
import com.teamwiney.ui.components.TopBar
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun MyPageBadgeScreen(
    appState: WineyAppState
) {
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
        MyPageSommelierBadge(badges = emptyList())
        HeightSpacerWithLine(color = WineyTheme.colors.gray_900)
        MyPageActivityBadge(badges = emptyList())
    }
}

@Composable
fun MyPageSommelierBadge(
    badges: List<WineBadge>
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
            items(
                List(10) {
                    WineBadge(
                        badgeId = 1L,
                        badgeType = BadgeType.SOMMELIER,
                        name = "배지 이름",
                        acquisitionMethod = "취득 방법",
                        description = "뱃지 설명",
                        acquiredAt = "취득일",
                        isRead = false,
                        badgeImage = ""
                    )
                }
            ) { badge ->
                WineBadgeItem(
                    modifier = Modifier.width(itemWidth),
                    wineBadge = badge
                )
            }
        }
    }
}

@Composable
fun MyPageActivityBadge(
    badges: List<WineBadge>
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
            modifier = Modifier.padding(vertical = 20.dp),
            rows = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(
                List(10) {
                    WineBadge(
                        badgeId = 1L,
                        badgeType = BadgeType.SOMMELIER,
                        name = "배지 이름",
                        acquisitionMethod = "취득 방법",
                        description = "뱃지 설명",
                        acquiredAt = "취득일",
                        isRead = false,
                        badgeImage = ""
                    )
                }
            ) { badge ->
                WineBadgeItem(
                    modifier = Modifier.width(itemWidth),
                    wineBadge = badge
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Preview
@Composable
fun WineBadgeItem(
    modifier: Modifier = Modifier,
    wineBadge: WineBadge = WineBadge(
        badgeId = 1L,
        badgeType = BadgeType.SOMMELIER,
        name = "배지 이름",
        acquisitionMethod = "취득 방법",
        description = "뱃지 설명",
        acquiredAt = "취득일",
        isRead = false,
        badgeImage = ""
    )
) {
    Column(modifier = modifier) {
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
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = wineBadge.acquiredAt ?: "미취득",
            style = WineyTheme.typography.captionM2.copy(
                color = WineyTheme.colors.gray_700
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}