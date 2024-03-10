package com.teamwiney.map.components

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
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
import java.net.URLEncoder


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ColumnScope.WineShopDetail(
    wineShop: WineShop,
    postBookmark: (WineShop) -> Unit,
    userPosition: LatLng
) {

    val context = LocalContext.current
    val navigateToNaverMap: (WineShop) -> Unit = { wineShop ->
        val url =
            "nmap://route/public?dlat=${wineShop.latitude}&dlng=${wineShop.longitude}&dname=${
                encodeUrlString(
                    wineShop.name
                )
            }&appname=com.teamwiney.winey"

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addCategory(Intent.CATEGORY_BROWSABLE)

        val installCheck = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.packageManager.queryIntentActivities(
                Intent(Intent.ACTION_MAIN, null).addCategory(Intent.CATEGORY_LAUNCHER),
                PackageManager.ResolveInfoFlags.of(PackageManager.MATCH_DEFAULT_ONLY.toLong())
            )
        } else {
            context.packageManager.queryIntentActivities(
                Intent(Intent.ACTION_MAIN, null).addCategory(Intent.CATEGORY_LAUNCHER),
                PackageManager.GET_META_DATA
            )
        }

        // 네이버맵이 설치되어 있다면 앱으로 연결, 설치되어 있지 않다면 스토어로 이동
        if (installCheck.isEmpty()) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=com.nhn.android.nmap")
                )
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
                    wineShop.shopMoods.take(3).forEach {
                        Text(
                            text = it,
                            color = WineyTheme.colors.gray_500,
                            style = WineyTheme.typography.captionM1,
                            modifier = Modifier
                                .clip(RoundedCornerShape(40.dp))
                                .border(
                                    BorderStroke(1.dp, WineyTheme.colors.gray_800),
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
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_time_baseline_19),
                    contentDescription = "IC_GPS",
                    modifier = Modifier
                        .size(19.dp),
                    tint = WineyTheme.colors.gray_50
                )
                Text(
                    text = businessHours.firstOrNull() ?: "정보가 없습니다.",
                    style = WineyTheme.typography.captionM1,
                    color = WineyTheme.colors.gray_50,
                )
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
            if (isExpanded) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.padding(start = 29.dp)
                ) {
                    businessHours.subList(1, businessHours.size)
                        .forEach {
                            Text(
                                text = it,
                                style = WineyTheme.typography.captionM1,
                                color = WineyTheme.colors.gray_700
                            )
                        }
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
                val distanceMeter =
                    userPosition.distanceTo(LatLng(wineShop.latitude, wineShop.longitude))
                        .toInt()
                Text(
                    text = if (distanceMeter >= 1000) {
                        "${String.format("%.1f", distanceMeter / 1000.0)}km"
                    } else {
                        "${distanceMeter}m"
                    },
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

private fun encodeUrlString(input: String): String {
    return URLEncoder.encode(input, "UTF-8")
}