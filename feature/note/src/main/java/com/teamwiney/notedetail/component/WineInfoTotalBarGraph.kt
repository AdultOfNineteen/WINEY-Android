package com.teamwiney.notedetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.teamwiney.data.network.model.response.Wine
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.TasteScoreHorizontalBar
import com.teamwiney.ui.theme.WineyTheme


@Composable
fun WineInfoTotalBarGraph(
    progress: Float,
    wine: Wine
) {
    Column {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(WineyTheme.colors.main_2)
                        .size(12.dp)
                )
                Text(
                    text = "취향이 비슷한 사람들이 느낀 와인의 맛",
                    style = WineyTheme.typography.captionM2,
                    color = WineyTheme.colors.gray_50
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(WineyTheme.colors.point_1)
                        .size(12.dp)
                )
                Text(
                    text = "와인의 기본 맛",
                    style = WineyTheme.typography.captionM2,
                    color = WineyTheme.colors.gray_50
                )
            }
        }

        HeightSpacer(height = 17.dp)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            TasteScoreHorizontalBar(
                progress = progress,
                label = "당도",
                peopleScore = wine.wineSummary.avgSweetness,
                defaultScore = wine.sweetness
            )

            TasteScoreHorizontalBar(
                progress = progress,
                label = "산도",
                peopleScore = wine.wineSummary.avgAcidity,
                defaultScore = wine.acidity
            )

            TasteScoreHorizontalBar(
                progress = progress,
                label = "바디",
                peopleScore = wine.wineSummary.avgBody,
                defaultScore = wine.body
            )

            TasteScoreHorizontalBar(
                progress = progress,
                label = "탄닌",
                peopleScore = wine.wineSummary.avgTannins,
                defaultScore = wine.tannins
            )
        }
    }
}
