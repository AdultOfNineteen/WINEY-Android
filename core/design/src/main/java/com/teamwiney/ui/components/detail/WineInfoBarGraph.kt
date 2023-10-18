package com.teamwiney.ui.components.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.VerticalBarGraph
import com.teamwiney.ui.components.VerticalBarGraphData
import com.teamwiney.ui.theme.WineyTheme


@Composable
fun WineInfoBarGraph(
    progress: Float,
    taste: String,
    default: Int,
    similar: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeightSpacer(height = 42.dp)

        Text(
            modifier = Modifier,
            text = taste,
            style = WineyTheme.typography.headline.copy(color = Color.White)
        )

        HeightSpacer(height = 36.dp)

        VerticalBarGraph(
            progress = progress,
            data = listOf(
                VerticalBarGraphData(
                    label = "와인의 기본맛",
                    score = default,
                    color = WineyTheme.colors.main_2
                ),
                VerticalBarGraphData(
                    label = "취향이 비슷한 사람들이\n느낀 와인의 맛",
                    score = similar,
                    color = WineyTheme.colors.point_1
                )
            )
        )
    }
}
