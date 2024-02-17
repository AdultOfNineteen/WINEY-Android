package com.teamwiney.map.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
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
internal fun BoxScope.WineBottomSheetOpenPopUp(
    isCollapsed: Boolean,
    expandBottomSheet: () -> Unit,
    peekHeight: Dp,
) {
    AnimatedVisibility(
        visible = isCollapsed,
        modifier = Modifier.align(Alignment.BottomCenter),
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Row(
            modifier = Modifier
                .padding(bottom = peekHeight + 24.dp)
                .clip(RoundedCornerShape(42.dp))
                .background(WineyTheme.colors.gray_900)
                .clickable {
                    expandBottomSheet()
                }
                .padding(17.dp, 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(text = "목록열기")
            Icon(
                painter = painterResource(id = R.drawable.ic_hamburg_baseline_13),
                contentDescription = "IC_GPS",
                modifier = Modifier
                    .size(13.dp),
                tint = WineyTheme.colors.gray_50
            )
        }
    }
}

