package com.teamwiney.map.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.teamwiney.core.design.R
import com.teamwiney.data.network.model.response.WineShop
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.HeightSpacerWithLine
import com.teamwiney.ui.theme.WineyTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.WineShopItem(
    wineShop: WineShop,
    postBookmark: (WineShop) -> Unit,
    setSelectedMarker: (WineShop) -> Unit
) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .padding(24.dp)
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
            .clickable {
                setSelectedMarker(wineShop)
            }
            .animateItemPlacement()
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .placeholder(R.drawable.img_dummy_wine)
                .error(R.drawable.img_dummy_wine)
                .data(wineShop.imgUrl)
                .build(),
            contentDescription = "IMG_WINE",
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .size(110.dp, 100.dp)
                .background(WineyTheme.colors.gray_900),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(17.dp))
        Column(
            Modifier
                .weight(1f)
                .wrapContentHeight()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = wineShop.name,
                    style = WineyTheme.typography.headline,
                    color = WineyTheme.colors.gray_50,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                )
                Text(
                    text = wineShop.shopType,
                    style = WineyTheme.typography.captionM1,
                    color = WineyTheme.colors.gray_500,
                    maxLines = 1,
                    overflow = TextOverflow.Visible,
                )
            }
            HeightSpacer(height = 7.dp)
            Text(
                text = wineShop.address,
                style = WineyTheme.typography.captionM1,
                color = WineyTheme.colors.gray_700,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier.horizontalScroll(rememberScrollState())
            ) {
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
            painter = painterResource(
                id =
                if (wineShop.like) R.drawable.ic_bookmark_fill_24 else R.drawable.ic_bookmark_baseline_24
            ),
            contentDescription = "IC_BOOKMARK",
            modifier = Modifier
                .size(24.dp)
                .clickable {
                    postBookmark(wineShop)
                },
            tint = WineyTheme.colors.main_2
        )
    }
    HeightSpacerWithLine(color = WineyTheme.colors.gray_900)
}