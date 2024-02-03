package com.teamwiney.ui.components.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.teamwiney.core.design.R
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun WineTitleAndDescription(
    type: String,
    name: String,
) {
    HeightSpacer(height = 20.dp)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            modifier = Modifier.height(68.dp),
            text = type,
            style = WineyTheme.typography.display1,
            color = WineyTheme.colors.gray_50
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_thismooth),
            contentDescription = null,
            tint = Color.Unspecified
        )
    }
    Text(
        text = name,
        style = WineyTheme.typography.bodyB2,
        color = WineyTheme.colors.gray_500
    )
}