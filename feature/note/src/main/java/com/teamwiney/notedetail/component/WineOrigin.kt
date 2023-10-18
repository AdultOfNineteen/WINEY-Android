package com.teamwiney.notedetail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.teamwiney.data.network.model.response.TastingNoteDetail
import com.teamwiney.ui.components.WineBadge
import com.teamwiney.ui.theme.WineyTheme


@Composable
fun WineOrigin(
    wine: TastingNoteDetail
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        WineBadge(color = wine.wineType)

        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "national an thems",
                    style = WineyTheme.typography.captionM3,
                    color = WineyTheme.colors.gray_50
                )

                Text(
                    text = wine.region,
                    style = WineyTheme.typography.captionB1,
                    color = WineyTheme.colors.gray_50
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Varieties",
                    style = WineyTheme.typography.captionM3,
                    color = WineyTheme.colors.gray_50
                )

                Text(
                    text = wine.varietal,
                    style = WineyTheme.typography.captionB1,
                    color = WineyTheme.colors.gray_50
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "ABV",
                    style = WineyTheme.typography.captionM3,
                    color = WineyTheme.colors.gray_50
                )

                Text(
                    text = "${wine.officialAlcohol.toInt()}%",
                    style = WineyTheme.typography.captionB1,
                    color = WineyTheme.colors.gray_50
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Purchase price",
                    style = WineyTheme.typography.captionM3,
                    color = WineyTheme.colors.gray_50
                )

                Text(
                    text = "${wine.price}",
                    style = WineyTheme.typography.captionB1,
                    color = WineyTheme.colors.gray_50
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Vintage",
                    style = WineyTheme.typography.captionM3,
                    color = WineyTheme.colors.gray_50
                )

                Text(
                    text = "${wine.noteDate}",
                    style = WineyTheme.typography.captionB1,
                    color = WineyTheme.colors.gray_50
                )
            }
        }
    }
}
