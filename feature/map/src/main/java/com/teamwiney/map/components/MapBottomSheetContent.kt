@file:OptIn(ExperimentalGlideComposeApi::class)

package com.teamwiney.map.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.teamwiney.core.design.R
import com.teamwiney.data.network.model.response.WineShop
import com.teamwiney.map.model.ShopwFilter
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.HeightSpacerWithLine
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun MapBottomSheetContent(
    isExpanded: Boolean,
    contentHeight: Dp,
    filter: ShopwFilter,
    wineShops: List<WineShop>,
) {
    Column(
        modifier = Modifier
            .navigationBarsPadding()
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = WineyTheme.colors.gray_950),
    ) {
        Spacer(
            modifier = Modifier
                .padding(top = 10.dp, bottom = 20.dp)
                .width(66.dp)
                .height(6.dp)
                .background(WineyTheme.colors.gray_800)
                .align(Alignment.CenterHorizontally)
        )

        Column(
            modifier = Modifier
                .height(contentHeight)
                .verticalScroll(rememberScrollState())
        ) {

            when (filter) {
                // 내 장소
                ShopwFilter.LIKE -> {
                    Row(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 13.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "내 장소",
                            style = WineyTheme.typography.title2,
                            color = WineyTheme.colors.gray_50
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_bookmark_fill_24),
                            tint = WineyTheme.colors.gray_50,
                            contentDescription = "IC_BOOKMARK",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    HeightSpacer(height = 6.dp)
                    Text(
                        text = "저장 N개",
                        style = WineyTheme.typography.captionM1,
                        color = WineyTheme.colors.gray_700,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    HeightSpacerWithLine(
                        color = WineyTheme.colors.gray_900,
                        modifier = Modifier.padding(vertical = 20.dp)
                    )
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterHorizontally)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_note_unselected),
                            contentDescription = "IMG_NOTE",
                            modifier = Modifier.size(112.dp)
                        )
                        Text(
                            text = "아직 저장된 장소가 없어요 :(",
                            style = WineyTheme.typography.headline,
                            color = WineyTheme.colors.gray_800,
                            modifier = Modifier.padding(top = 13.dp)
                        )
                        Text(
                            text = "마음에 드는 장소를 모아봐요",
                            style = WineyTheme.typography.bodyM2,
                            color = WineyTheme.colors.gray_800,
                            modifier = Modifier.padding(top = 6.dp)
                        )
                    }
                }

                // 디테일 화면
                ShopwFilter.RESTAURANT -> {
                    Image(
                        painter = painterResource(id = R.drawable.img_dummy_wine),
                        contentDescription = "IMG_WINE",
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1.8f)
                            .padding(top = 13.dp),
                        contentScale = ContentScale.Crop
                    )
                    Column(
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxWidth()
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Row {
                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "모이니 와인바",
                                        style = WineyTheme.typography.headline,
                                        color = WineyTheme.colors.gray_50
                                    )
                                    Text(
                                        text = "와인바",
                                        style = WineyTheme.typography.captionM1,
                                        color = WineyTheme.colors.gray_500
                                    )
                                }
                                Text(
                                    text = "주소값 마포구 신곡덩독",
                                    modifier = Modifier.padding(top = 7.dp),
                                    style = WineyTheme.typography.captionM1,
                                    color = WineyTheme.colors.gray_700
                                )
                                Row(modifier = Modifier.padding(top = 14.dp)) {
                                    listOf("양식", "프랑스", "파스타", "파스타", "파스타").forEach {
                                        Text(
                                            text = it,
                                            color = WineyTheme.colors.gray_500,
                                            style = WineyTheme.typography.captionM1,
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(40.dp))
                                                .border(
                                                    BorderStroke(
                                                        1.dp, WineyTheme.colors.gray_800
                                                    ),
                                                    RoundedCornerShape(40.dp)
                                                )
                                                .padding(horizontal = 10.dp, vertical = 5.dp)
                                        )
                                    }
                                }
                            }
                            Icon(
                                painter = painterResource(id = R.drawable.ic_bookmark_fill_24),
                                contentDescription = "IC_BOOKMARK",
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable {
                                        // TODO 북마크
                                    },
                                tint = WineyTheme.colors.main_2
                            )
                        }
                        HeightSpacer(height = 20.dp)
                        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_gps_baseline_22),
                                    contentDescription = "IC_GPS",
                                    modifier = Modifier
                                        .size(19.dp),
                                    tint = WineyTheme.colors.gray_50
                                )
                                Text(
                                    text = "월~화 10:00 ~ 14:00 ",
                                    style = WineyTheme.typography.captionM1,
                                    color = WineyTheme.colors.gray_50,
                                    textDecoration = TextDecoration.Underline
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_gps_baseline_22),
                                    contentDescription = "IC_GPS",
                                    modifier = Modifier
                                        .size(19.dp),
                                    tint = WineyTheme.colors.gray_50
                                )
                                Text(
                                    text = "송파구 올림픽로 37길 2층 ",
                                    style = WineyTheme.typography.captionM1,
                                    color = WineyTheme.colors.gray_50,
                                    textDecoration = TextDecoration.Underline
                                )
                                Text(
                                    text = "425m",
                                    style = WineyTheme.typography.captionM1,
                                    color = WineyTheme.colors.gray_800
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_gps_baseline_22),
                                    contentDescription = "IC_GPS",
                                    modifier = Modifier
                                        .size(19.dp),
                                    tint = WineyTheme.colors.gray_50
                                )
                                Text(
                                    text = "000-000-0000 ",
                                    style = WineyTheme.typography.captionM1,
                                    color = WineyTheme.colors.gray_50,
                                    textDecoration = TextDecoration.Underline
                                )
                            }
                        }

                    }

                }

                // 이 외
                else -> {
                    wineShops.forEach { wineShop ->
                        Row(
                            modifier = Modifier
                                .padding(24.dp)
                                .fillMaxWidth()
                        ) {
                            GlideImage(
                                model = wineShop.imgUrl,
                                contentDescription = "IMG_WINE",
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .size(110.dp, 100.dp),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(17.dp))
                            Column(Modifier.weight(1f)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = wineShop.name,
                                        style = WineyTheme.typography.headline,
                                        color = WineyTheme.colors.gray_50
                                    )
                                    Text(
                                        text = wineShop.shopType,
                                        style = WineyTheme.typography.captionM1,
                                        color = WineyTheme.colors.gray_500
                                    )
                                }
                                HeightSpacer(height = 7.dp)
                                Text(
                                    text = wineShop.address,
                                    style = WineyTheme.typography.captionM1,
                                    color = WineyTheme.colors.gray_700
                                )
                                HeightSpacer(height = 14.dp)
                                Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                                    wineShop.shopMoods.forEach {
                                        Text(
                                            text = it,
                                            color = WineyTheme.colors.gray_500,
                                            style = WineyTheme.typography.captionM1,
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(40.dp))
                                                .border(
                                                    BorderStroke(
                                                        1.dp, WineyTheme.colors.gray_800
                                                    ),
                                                    RoundedCornerShape(40.dp)
                                                )
                                                .padding(horizontal = 10.dp, vertical = 5.dp)
                                        )
                                    }
                                }

                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                painter = painterResource(id = R.drawable.ic_bookemark_baseline_24),
                                contentDescription = "IC_BOOKMARK",
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable {
                                        // TODO 북마크
                                    },
                                tint = WineyTheme.colors.main_2
                            )
                        }
                        HeightSpacerWithLine(color = WineyTheme.colors.gray_900)
                    }
                }
            }
        }
    }
}

