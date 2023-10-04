package com.teamwiney.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.teamwiney.core.design.R

data class BadgeProperties(
    val badgeImage: Int,
    val wineImage: Int
)

@Composable
fun WineBadge(
    color: String
) {
    val (badge, image) = when (color) {
        "RED" -> BadgeProperties(
            R.drawable.ic_red_wine_badge,
            R.drawable.ic_red_wine
        )
        "WHITE" -> BadgeProperties(
            R.drawable.ic_white_wine_badge,
            R.drawable.ic_white_wine
        )
        "ROSE" -> BadgeProperties(
            R.drawable.ic_rose_wine_badge,
            R.drawable.ic_rose_wine
        )
        "SPARKL" -> BadgeProperties(
            R.drawable.ic_sparkl_wine_badge,
            R.drawable.ic_sparkl_wine
        )
        "PORT" -> BadgeProperties(
            R.drawable.ic_port_wine_badge,
            R.drawable.ic_port_wine
        )
        else -> BadgeProperties(
            R.drawable.ic_etc_wine_badge,
            R.drawable.ic_etc_wine
        )
    }

    Box(
        modifier = Modifier.size(148.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painterResource(id = badge),
            contentDescription = null
        )

        Image(
            painter = painterResource(id = image),
            contentDescription = null
        )
    }
}
