package com.teamwiney.map.components

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.naver.maps.geometry.LatLng
import com.teamwiney.core.design.R
import com.teamwiney.data.network.model.response.WineShop
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.theme.WineyTheme


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ColumnScope.WineShopDetail(
    wineShop: WineShop,
    postBookmark: (WineShop) -> Unit,
    userPosition: LatLng
) {

    val context = LocalContext.current
    val navigateToNaverMap : (WineShop)->Unit = { wineShop->
        val url =
            "nmap://route/public?dlat=${wineShop.latitude}&dlng=${wineShop.longitude}&dname=${wineShop.name}&appname=cocktaildakk"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addCategory(Intent.CATEGORY_BROWSABLE)

        val list: List<ResolveInfo> =
            context.packageManager.queryIntentActivities(
                intent,
                PackageManager.MATCH_DEFAULT_ONLY,
            )
        if (list.isEmpty()) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=com.nhn.android.nmap"),
                ),
            )
        } else {
            context.startActivity(intent)
        }
    }

    val navigateToCall = { wineShop: WineShop ->
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${wineShop.phone.replace("-", "")}"))
        context.startActivity(intent)
    }

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
                Text(
                    text = wineShop.address,
                    modifier = Modifier.padding(top = 7.dp),
                    style = WineyTheme.typography.captionM1,
                    color = WineyTheme.colors.gray_700
                )
                FlowRow(
                    modifier = Modifier.padding(top = 14.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
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
            Icon(
                painter = painterResource(id = if (wineShop.like) R.drawable.ic_bookmark_fill_24 else R.drawable.ic_bookmark_baseline_24),
                contentDescription = "IC_BOOKMARK",
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        postBookmark(wineShop)
                    },
                tint = WineyTheme.colors.main_2
            )
        }
        HeightSpacer(height = 20.dp)

        var isExpanded by remember {
            mutableStateOf(false)
        }
        val businessHours = wineShop.businessHour.split("\n")

        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_time_baseline_19),
                    contentDescription = "IC_GPS",
                    modifier = Modifier
                        .size(19.dp),
                    tint = WineyTheme.colors.gray_50
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    if (isExpanded) {
                        businessHours.forEachIndexed { index, it ->
                            Text(
                                text = it,
                                style = WineyTheme.typography.captionM1,
                                color = if (index == 0) WineyTheme.colors.gray_50 else WineyTheme.colors.gray_700
                            )
                        }
                    } else {
                        Text(
                            text = businessHours.firstOrNull() ?: "정보가 없습니다.",
                            style = WineyTheme.typography.captionM1,
                            color = WineyTheme.colors.gray_50,
                        )
                    }
                }

                if (businessHours.size > 1) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_right),
                        contentDescription = "IC_ARROW",
                        modifier = Modifier
                            .size(19.dp)
                            .clickable {
                                isExpanded = !isExpanded
                            }
                            .rotate(if (isExpanded) 270f else 90f),
                        tint = WineyTheme.colors.gray_50
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.clickable {
                    navigateToNaverMap(wineShop)
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_location_baseline_19),
                    contentDescription = "IC_GPS",
                    modifier = Modifier
                        .size(19.dp),
                    tint = WineyTheme.colors.gray_50
                )
                Text(
                    text = wineShop.address,
                    style = WineyTheme.typography.captionM1,
                    color = WineyTheme.colors.gray_50,
                    textDecoration = TextDecoration.Underline
                )
                Text(
                    text = userPosition.distanceTo(LatLng(wineShop.latitude, wineShop.longitude)).toInt().toString() + "m",
                    style = WineyTheme.typography.captionM1,
                    color = WineyTheme.colors.gray_800
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .clickable {
                        navigateToCall(wineShop)
                    },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_phone_baseline_19),
                    contentDescription = "IC_GPS",
                    modifier = Modifier
                        .size(19.dp),
                    tint = WineyTheme.colors.gray_50
                )
                Text(
                    text = wineShop.phone,
                    style = WineyTheme.typography.captionM1,
                    color = WineyTheme.colors.gray_50,
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    }
}
