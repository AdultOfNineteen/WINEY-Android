package com.teamwiney.map.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.teamwiney.core.design.R
import com.teamwiney.ui.theme.WineyTheme

@Composable
internal fun BoxScope.GpsIcon(
    isCollapsed: Boolean,
    peekHeight: Dp,
    onClickGPSIcon: () -> Unit
) {
    AnimatedVisibility(
        visible = isCollapsed,
        modifier = Modifier.align(Alignment.BottomEnd),
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = Modifier
                .padding(
                    end = 20.dp,
                    bottom = peekHeight + 44.dp
                )
                .clip(CircleShape)
                .background(WineyTheme.colors.gray_900)
                .clickable {
                    onClickGPSIcon()
                }
                .padding(13.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_gps_baseline_22),
                contentDescription = "IC_GPS",
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(22.dp),
                tint = WineyTheme.colors.gray_50
            )
        }
    }
}