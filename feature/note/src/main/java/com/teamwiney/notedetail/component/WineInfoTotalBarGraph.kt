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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.ScoreHorizontalBar
import com.teamwiney.ui.theme.WineyTheme


@Composable
fun WineInfoTotalBarGraph(
    progress: Float,
    label: String,
    labelColor: Color,
    data: List<Pair<String, Int>>
) {
    Column {
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
                text = label,
                style = WineyTheme.typography.captionM2,
                color = WineyTheme.colors.gray_50
            )
        }
        
        HeightSpacer(height = 20.dp)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(377.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            data.forEach { (label, score) ->
                if (score == 0) {
                    ScoreHorizontalBar(
                        progress = progress,
                        label = label,
                        score = score,
                        labelColor = WineyTheme.colors.gray_900,
                        barColor = WineyTheme.colors.gray_900
                    )
                } else {
                    ScoreHorizontalBar(
                        progress = progress,
                        label = label,
                        score = score,
                        labelColor = WineyTheme.colors.gray_50,
                        barColor = labelColor
                    )
                }
            }
        }
    }
}
