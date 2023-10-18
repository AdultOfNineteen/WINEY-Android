package com.teamwiney.notecollection.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun NoteSelectedFilterChip(
    name: String,
    isEnable: Boolean = true,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(40.dp))
            .background(if (isEnable) WineyTheme.colors.gray_900 else Color.Transparent)
            .border(
                BorderStroke(
                    1.dp,
                    if (isEnable) Color.Transparent else WineyTheme.colors.gray_900
                ),
                RoundedCornerShape(40.dp)
            )
            .padding(10.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = name,
            color = if (isEnable) WineyTheme.colors.gray_50 else WineyTheme.colors.gray_700,
            style = WineyTheme.typography.captionB1
        )

        Icon(
            painter = painterResource(id = com.teamwiney.core.design.R.drawable.ic_arrow_down),
            contentDescription = "IC_ARROW_DOWN",
            tint = Color.Unspecified
        )
    }
}

@Composable
fun ResetFilterButton(
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(40.dp))
            .background(Color.Transparent)
            .border(
                BorderStroke(
                    1.dp,
                    WineyTheme.colors.gray_900
                ),
                RoundedCornerShape(40.dp)
            )
            .padding(10.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Text(
            text = "초기화",
            color = WineyTheme.colors.gray_700,
            style = WineyTheme.typography.captionB1
        )

        Icon(
            painter = painterResource(id = com.teamwiney.core.design.R.drawable.ic_refresh_20),
            contentDescription = "IC_REFERSH",
            tint = WineyTheme.colors.gray_50,
            modifier = Modifier.size(12.dp)
        )
    }
}
